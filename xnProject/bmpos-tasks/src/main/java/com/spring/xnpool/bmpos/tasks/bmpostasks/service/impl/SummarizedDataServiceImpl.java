package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.*;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.SharesMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.TransacTionsMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.UpdateException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.AccountAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.WalletProxyAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.producer.Producer;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.ShareService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.SummarizedData;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class SummarizedDataServiceImpl implements SummarizedData {
    @Autowired
    private AccountAPI accountAPI;
    @Autowired
    private WalletProxyAPI walletProxyAPI;
    //更新时间
    private Date updatetime;
    @Autowired
    private ShareService shareService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    HttpAPIService httpAPIService;
    @Autowired
    SharesMapper sharesMapper;
    @Autowired
    TransacTionsMapper transacTionsMapper;
    Map<String, Object> map = new HashMap<>();
    private static String accountIp;

    @Value("${httpClient.account-ip}")
    public void getaccountIp(String accountIps) {
        accountIp = accountIps;
    }

    private static String wallentIp;

    @Value("${httpClient.wallet-ip}")
    public void getWallentIp(String wallentIps) {
        wallentIp = wallentIps;
    }
    @Autowired
    Producer producer;
    private SimpleDateFormat simpleDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//转换年月日
    private SimpleDateFormat simplehour = new SimpleDateFormat("yyMMdd:HH");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd:HH:mm");//只取年月日
    private SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd HH:mm");
    private Calendar calendar = new GregorianCalendar();

    @Override
    public void sumDate(String coin) throws ParseException {
        Long start = System.currentTimeMillis();
        String lastIndexStr = getValueByName("pps_last_share_id");
        Integer lastIndex = Integer.valueOf(lastIndexStr);
        String startIndexStr = getValueByName("start_share_id");
        Integer startIndex = Integer.valueOf(startIndexStr);
        log.info("已结算的开始ID为:" + startIndex);
        log.info("已结算的结束ID为:" + lastIndex);
        if (startIndex >= lastIndex) {
            log.info("数据未更新,等待下次结算数据!!!");
            return;
        }

        //开起数据库定时
        sharesMapper.OpenTimer();
        //获取跟新时间
        String updateDateTime = shareService.getTimeById(startIndex);

        if (!StringUtils.isEmpty(updateDateTime)) {
            updatetime = simpleDateTime.parse(updateDateTime);
            if (updatetime == null) {
                log.info("无效更新时间!");
                return;
            }
            //从setting表中读取设置时间,获取0点时间
            String settingTime = getValueByName("start_share_time");
            Date startTime = simpleDateTime.parse(settingTime);
            Date zeroTime = getZeroTime(startTime);
            //获取开始时间及结算时间
            Map<String, Date> dataMap15min = getStartAndEndTime(startTime);//15分钟区间
            Map<String, Date> dataMap1hour = getOneHourStartAndEndTime(startTime);//1小时区间
            log.info("零点时间:" + zeroTime);

            /*======================开始缓存=======================*/
            List<String> totalList = shareService.selectusernameByDay(7);
            for (String username : totalList) {
                String user = username.substring(0, username.lastIndexOf("."));
                //总矿机矿机
                redisUtil.sSet(coin.toUpperCase() + ":TOTAL:" + user, username);
            }
            //获取所有用户:
            List<UserCount> users = shareService.selectUser(startIndex, lastIndex);
            //获取所有用户下矿机:
            List<UserNameTime> userNameTimes = shareService.selectUsername(startIndex, lastIndex);
            //在线用户列表
            List<String> onlineList = new ArrayList<>();
            for (UserNameTime userNameTime : userNameTimes) {
                String username = userNameTime.getUsername();
                String user = username.substring(0, username.lastIndexOf("."));
                //在线矿机
                onlineList.add(username);
                redisUtil.sSet(coin.toUpperCase() + ":ONLINE:" + user, username);
            }
            //离线矿机
            Collection exists=new ArrayList(totalList);
            Collection notexists=new ArrayList(totalList);
             boolean result = notexists.removeAll(onlineList);
             System.err.println(result);
            System.err.println("notexists"+notexists.size());
            exists.removeAll(notexists);

            if(!notexists.isEmpty()){
                for(Object mill:notexists){
                    String username = mill.toString();
                    String user = username.substring(0, username.lastIndexOf("."));
                    String millname = username.substring(user.length()+1);
                    JSONObject jsonObject  = accountAPI.getByAidAndCurrency(user,coin);
                    System.err.println(jsonObject);
                    if(jsonObject.getInt("state")==200){
                        String coinAddresses = jsonObject.getJSONObject("data").getString("coinAddress");
                        jsonObject = accountAPI.getNameByOldName(millname,coin);
                        System.err.println(jsonObject+"=========");
                        millname = jsonObject.getString("data");
                         //todo 测试实际返回更改的矿机名
                        OffMill offMill = new OffMill();
                        offMill.setUser(user);
                        offMill.setMillName(millname);
                        offMill.setAddress(coinAddresses);
                        offMill.setCoin(coin);
                        offMill.setTime(updateDateTime);
                        producer.send("offline",offMill);
                        //离线线矿机
                        redisUtil.sSet(coin.toUpperCase() + ":OFFLINE:" + user, username);
                    }

                }
            }
            //todo 转移数据
            shareService.dumpHistory(startIndex, lastIndex);//转移到历史表并删除shares表
            //每小时区间
            Map<String, Date> oneTimeMap = getOneHourTimeZore(updatetime);
            ExecutorService service = Executors.newFixedThreadPool(8);
            service.execute(() -> findOne(dataMap15min, coin));//将一个矿机下的每间隔15分钟的数据存入缓存
            service.execute(() -> findOneWorkerOneDayData(dataMap1hour, coin));//将一个矿机的每天24小时数据存入缓存(用来做周和月的算力)
            service.execute(() -> findUserAllWorkerAverageForce(dataMap1hour, coin));//查询账户下所有的矿机每一个小时的平均算力
            service.execute(() -> getUsernameDifficulty(updatetime, coin, users,oneTimeMap));//用户状态(3个月有效)
            service.execute(() -> getDifficulty153060(updatetime, coin, userNameTimes,oneTimeMap));//6.矿机状态(7天有效)
            service.execute(() -> poolDifficultie(dataMap1hour, coin)); //1.获取24小时矿池算力
            service.execute(() -> poolState(updatetime, zeroTime, coin, users));//2.矿池状态
            service.shutdown();
            //以下是等线程池的全部线程执行结束后，会自动执行。
            while (true) {
                if (service.isTerminated()) {
                    long time1 = System.currentTimeMillis() - start;
                    System.out.println("[" + new Date() + "]:" + "程序结束了，总耗时：" + time1 + " ms(毫秒)！\n");
                    break;
                }
            }
            Long end = System.currentTimeMillis();
            log.info("总耗时:" + (end - start) / 1000);
           update("start_share_id", lastIndex + 1 + "");
           update("start_share_time", updateDateTime);
        } else {
            log.info("SummarizedDataServiceImpl ----  updateDateTime  为空");
        }
    }

    /**
     * 1.矿池算力(31天有效)
     *
     * @param
     * @param
     */

    public void poolDifficultie(Map<String, Date> dateMap, String coin) {
        Date startTime = dateMap.get("startTime");
        Date lastTime = dateMap.get("endTime");
        List<Difficulty> difficulties = shareService.select24DifficultyByzeroTime(startTime, lastTime);
        if (difficulties.isEmpty()) {
            log.info("未找到矿池数据!");
            return;
        }
        log.info("24小时矿池算力:" + difficulties);
        for (Difficulty data : difficulties) {
            String time = simplehour.format(data.getTime());
            String[] dateTime = time.split(":");
            Float difficultie = data.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":POOL:SHARE:" + dateTime[0], dateTime[1], difficultie, 31 * 24 * 60 * 60 * 1000);
        }
    }

    /**
     * 2.矿池状态(永久有效)
     *
     * @param
     */
    public void poolState(Date updateDateTime, Date zeroTime, String coin, List<UserCount> users) {
        Long updateDate = updateDateTime.getTime();
        Long before5M = updateDate - (5 * 60 * 1000);
        Date before5MTime = new Date(before5M);
        log.info("before5MTime:" + before5MTime);
        log.info("updateDateTime:" + updateDateTime);
        Double avgDifficulty = shareService.selectPoolAvgDifficulty(zeroTime, updateDateTime);
        if (avgDifficulty == null) {
            log.info("无平均算力");
            return;
        }
        JSONObject data = new JSONObject();
        data.put("avgDifficulty", avgDifficulty);//日平均算力
        PoolDifficulty poolDifficulty = shareService.selectPoolWorks(before5MTime, updateDateTime);
        if (poolDifficulty == null) {
            log.info("无矿池算力");
            return;
        }
        Map<String, Object> licent = new HashMap<>();
        licent.put("coin", "ltc");
        JSONObject jsonObject = walletProxyAPI.getpool(coin);
        String bockPrice = jsonObject.getJSONObject("data").getString("blockPrice");
        String netHashrate = jsonObject.getJSONObject("data").getString("netHashrate");
        String netDifficulty = jsonObject.getJSONObject("data").getString("netDifficulty");
        String fee = jsonObject.getJSONObject("data").getString("fee");
        String blocks = jsonObject.getJSONObject("data").getString("blocks");
        data.put("bockPrice", bockPrice);
        data.put("netHashrate", netHashrate);//全网算力
        data.put("fee", fee);//费用
        data.put("netDifficulty", netDifficulty);//全网难度==当前难度
        data.put("bockPrice", bockPrice);
        data.put("blocks", blocks);//当前区块


        //总矿机数,一般默认7天内数据可修改
        List<String> usernameTotal  = sharesMapper.findUsernameByPool(7);
        //总矿工数,一般默认7天内数据可修改
        List<String> usersTotal = sharesMapper.findUserByPool(7);
        data.put("usernameTotal",usernameTotal.size());
        data.put("usersTotal",usersTotal.size());
        log.error("在线矿机:" + poolDifficulty);
        data.put("OnlineMiners", users.size());//在线矿工
        data.put("OnlineMill", poolDifficulty.getCount());//在线矿机
        data.put("poolDifficulty", poolDifficulty.getDifficulty());//矿池算力
        redisUtil.set(coin.toUpperCase() + ":POOL:STATE", data.toString());

    }

    /**
     * 3.账户算力(31天有效)
     * 查询账户下所有的矿机每一个小时的平均算力和实时算力
     *
     * @param map
     * @throws ParseException
     */
    public void findUserAllWorkerAverageForce(Map<String, Date> map, String coin) {
        Date startDate = map.get("startTime");
        Date endDate = map.get("endTime");
        String timeStr = simpleDateFormat.format(startDate);
        String bigKey = timeStr.substring(0, timeStr.indexOf(":"));
        List<AllWorkerAverageForce> userAllWorkerAverageForce = sharesMapper.findUserAllWorkerAverageForce(startDate, endDate);
        for (AllWorkerAverageForce allWorkerAverageForce : userAllWorkerAverageForce) {
            String user = allWorkerAverageForce.getUser();
            String avgBigKey = coin.toUpperCase() + ":ACC:SHIS:" + user + ":AVG:" + bigKey;//平均算力
            String realBigKey = coin.toUpperCase() + ":ACC:SHIS:" + user + ":REAL:" + bigKey;//实时算力
            Date Date = allWorkerAverageForce.getStartTime();//取出时间
            String startTime = simpleDateTime.format(Date);
            startTime = startTime.substring(startTime.indexOf(" ")).trim();
            String smallKey = startTime.substring(0, startTime.indexOf(":"));
            //获取平均算力
            Float avghaShrate = allWorkerAverageForce.getAvgHashrate();
            //获取实时算力
            Float realHashrate = allWorkerAverageForce.getRealHashrate();
            redisUtil.hset(avgBigKey, smallKey, avghaShrate);
            redisUtil.hset(realBigKey, smallKey, realHashrate);
        }
    }

    /**
     * 4.用户状态(3个月有效)
     *
     * @param
     * @param
     * @throws ParseException
     */
    public void getUsernameDifficulty(Date updateDateTime, String coin, List<UserCount> users,Map<String, Date> oneTimeMap) {
        Map<String, Date> oneDayMap = getStartWithEndTime();
        Date startDate = oneDayMap.get("startTime");
        Date endDate = oneDayMap.get("endTime");
        List<Valid> listValid=sharesMapper.findUserValid(startDate,endDate);
        for (Valid valid : listValid) {
            String user = valid.getUser();
            int count  = valid.getCount();
            String ourResult = valid.getOurResult();
            Date time = valid.getTime();
             String dateTimeStr = simplehour.format(time);
             String[] date = dateTimeStr.split(":");
            if(ourResult.equals("Y")){//有效率
                redisUtil.hset(coin.toUpperCase()+":ACCOUNT:VCOUNT:" + user, date[1], count);
            }else if(ourResult.equals("N")){//无效率
                redisUtil.hset(coin.toUpperCase()+":ACCOUNT:UNCOUNT:" + user, date[1], count);
            }

        }
        Date oneNow = oneTimeMap.get("startTime");
        Date oneEnd = oneTimeMap.get("endTime");
        //用户每小时算力
        List<UserDifficulty> oneHourDifficultys = shareService.selectUserDifficulty(oneNow, oneEnd);
        for(UserDifficulty userDifficulty:oneHourDifficultys){
            String user = userDifficulty.getUser();
            double difficulty = userDifficulty.getDifficulty();
            Date time = userDifficulty.getTime();
            String dateTimeStr = simplehour.format(time);
            String[] date = dateTimeStr.split(":");
            redisUtil.hset(coin.toUpperCase() + ":ACCOUNT:ONEHOUR:" + user,date[1],difficulty);
        }
        Long updateDate = updateDateTime.getTime();
        Long before60M = updateDate - (60 * 60 * 1000);
        Long before30M = updateDate - (30 * 60 * 1000);
        Long before15M = updateDate - (15 * 60 * 1000);
        Long before5M = updateDate - (5 * 60 * 1000);
        Date before60MTime = new Date(before60M);
        Date before30MTime = new Date(before30M);
        Date before15MTime = new Date(before15M);
        log.info("updateDateTime:" + updateDateTime);
        //todo 遍历 userDifficulty24Hs 60 30 15
        //从redis获取用户24小时算力
        for (UserCount userCount : users) {
            String user = userCount.getUser();
            JSONObject accountResult = accountAPI.getByAidAndCurrency(user,coin);
            //todo 如果返回 不是 200 表示没有 用户
            if (accountResult.getInt("state") == 200) {
                System.out.println("查找到的数据");
                Integer accountId = accountResult.getJSONObject("data").getInt("accountId");
                Double sumDifficulty24 = 0.0;
                Set<String> utilSubKey = redisUtil.getSubKey(coin.toUpperCase() + ":ONEHOUR:SHARE:" + user + "*");
                for (String subkey : utilSubKey) {
                    Object difficulty24 = redisUtil.get(subkey);
                    sumDifficulty24 += Double.valueOf(difficulty24.toString());
                }
                Double unpaid = transacTionsMapper.findbyType("Debit_Daily", accountId);
                Double blanace = transacTionsMapper.findbyType("Credit_PPS", accountId);
                Double total = transacTionsMapper.findbyType("finish_DebitDaily", accountId);
                redisUtil.hset(coin.toUpperCase() + ":ACCOUNT:STATE:" + user, "sumDifficulty24", sumDifficulty24, 90 * 24 * 60 * 60 * 1000);
                redisUtil.hset(coin.toUpperCase() + ":ACCOUNT:STATE:" + user, "unpaid", unpaid, 90 * 24 * 60 * 60 * 1000);
                redisUtil.hset(coin.toUpperCase() + ":ACCOUNT:STATE:" + user, "blanace", blanace, 90 * 24 * 60 * 60 * 1000);
                redisUtil.hset(coin.toUpperCase() + ":ACCOUNT:STATE:" + user, "total", total, 90 * 24 * 60 * 60 * 1000);
            } else {
                log.error("用户不存在!");
            }
        }
        List<UserDifficulty> userDifficulty60Ms = shareService.selectUserDifficulty(before60MTime, updateDateTime);
        for (UserDifficulty user60 : userDifficulty60Ms) {
            String user = user60.getUser();
            Double difficulty = user60.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":ACCOUNT:STATE:" + user, "sumDifficulty60", difficulty, 90 * 24 * 60 * 60 * 1000);
        }
        List<UserDifficulty> userDifficulty30Ms = shareService.selectUserDifficulty(before30MTime, updateDateTime);
        for (UserDifficulty user30 : userDifficulty30Ms) {
            String user = user30.getUser();
            Double difficulty = user30.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":ACCOUNT:STATE:" + user, "sumDifficulty30", difficulty, 90 * 24 * 60 * 60 * 1000);
        }
        List<UserDifficulty> userDifficulty15Ms = shareService.selectUserDifficulty(before15MTime, updateDateTime);
        for (UserDifficulty user15 : userDifficulty15Ms) {
            String user = user15.getUser();
            Double difficulty = user15.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":ACCOUNT:STATE:" + user, "sumDifficulty15", difficulty, 90 * 24 * 60 * 60 * 1000);
        }

    }


    /**
     * 5.矿机算力(31天有效)
     * 5.1.查询一个矿机下的一天数据,并且15分钟为一个区间(矿机算力)
     *
     * @param map
     * @throws ParseException
     */
    public void findOne(Map<String, Date> map, String coin) {
        Date startDate = map.get("startTime");
        Date endDate = map.get("endTime");
        List<Hashrate> floats = sharesMapper.avgHashrate(startDate, endDate);//一台矿机一天的数据
        for (int i = 0; i < floats.size(); i++) {
            Hashrate hashrate = floats.get(i);//取出单条数据
            String username = hashrate.getUsername();//取出用户名
            Date startTime = hashrate.getStartTime();//取出4个区间值起始值
            String timeStr = simpleDateFormat.format(startTime);
            String bigKey = timeStr.substring(0, timeStr.indexOf(":"));
            String avgBigKey = coin.toUpperCase() + ":ACC:SHARE:" + username + ":AVG:" + bigKey;//平均算力大KEY
            String realBigKey = coin.toUpperCase() + ":ACC:SHARE:" + username + ":REAL:" + bigKey;//实时算力大KEY
            long times = startTime.getTime();
            times = times + (15 * 1000 * 60);//区间值往后加15分钟
            startTime = new Date(times);//将longTime转成Date类型
            String key = simpleDateFormat.format(startTime);//时间戳往后累加15分钟后的字符串
            String smallKey = key.substring(key.indexOf(":") + 1);//小key字符串
            Integer realHashrateId = hashrate.getLastId();//取出每个区间的最后的实时算力ID
            Float realHashrate = sharesMapper.findRealHashrate(realHashrateId);//根据算力ID取到每个区间的实时算力
            float value = hashrate.getHashrate();//取出每个区间的平均算力
            redisUtil.hset(avgBigKey, smallKey, value);//平均存到缓存里面
            redisUtil.hset(realBigKey, smallKey, realHashrate);//实时存到缓存里面
        }
    }

    /**
     * 5.矿机算力(31天有效)
     * 5.2.查询一个矿机的一天24小时的数据(用来做周/月查询)
     *
     * @param map
     * @throws ParseException
     */
    public void findOneWorkerOneDayData(Map<String, Date> map, String coin) {
        Date startDate = map.get("startTime");
        Date endDate = map.get("endTime");
        //切割字符串进行拼接大Key
        String timeString = simpleDateTime.format(startDate);
        timeString = timeString.substring(0, timeString.indexOf(" "));
        String[] split = timeString.split("-");
        String year = split[0].substring(2, 4);
        String bigKey = year + split[1] + split[2];

        List<WeekShares> userOneWorkerWeekData = sharesMapper.findUserOneWorkerWeekData(endDate, startDate);
        for (int i = 0; i < userOneWorkerWeekData.size(); i++) {//遍历一台矿机一周内每天24小时的算力
            WeekShares weekShares = userOneWorkerWeekData.get(i);
            String username = weekShares.getUsername();
            String avgWeekBigKey = coin.toUpperCase() + ":ACC:SHHIS:" + username + ":AVG:" + bigKey;//每天24小时的平均算力大KEY
            String realWeekBigKey = coin.toUpperCase() + ":ACC:SHHIS:" + username + ":REAL:" + bigKey;//每天24小时的实时算力大KEY
            Date time = weekShares.getTime();
            String timeStr = simpleDateTime.format(time);
            timeStr = timeStr.substring(timeStr.indexOf(" ")).trim();
            String[] split1 = timeStr.split(":");
            String key = split1[0];
            Float avghashrate = weekShares.getAvghashrate();
            Float difficulty = weekShares.getDifficulty();
            redisUtil.hset(realWeekBigKey, key, difficulty);//一小时的真实算力存入缓存
            redisUtil.hset(avgWeekBigKey, key, avghashrate);//一小时的平均算力存入缓存
        }
    }


    /**
     * 6.矿机状态(7天有效)
     *
     * @param
     * @param
     * @throws ParseException
     */
    public void getDifficulty153060(Date updateDateTime, String coin, List<UserNameTime> usernames,Map<String, Date> oneTimeMap) {
        Date oneNow = oneTimeMap.get("startTime");
        Date oneEnd = oneTimeMap.get("endTime");
        //矿机有效份额无效份额
        List<MillValid> millValids = shareService.findUsernameValid(oneNow,oneEnd);
        for(MillValid mill:millValids){
            String username = mill.getUsername();
            int count = mill.getCount();
            String ourResult = mill.getOurResult();
            Date time = mill.getTime();
            String dateTimeStr = simplehour.format(time);
            String[] date = dateTimeStr.split(":");
            if(ourResult.equals("Y")) {//有效率
                redisUtil.hset(coin.toUpperCase()+":MILL:VCOUNT:" + username, date[1], count);
            }else if(ourResult.equals("N")){
                redisUtil.hset(coin.toUpperCase()+":MILL:UNVCOUNT:" + username, date[1], count);
            }

        }
        //获取每小时算力
         List<UsernameDifficulty> usernameDifficulties = shareService.findOneHourMill(oneNow,oneEnd);
        for(UsernameDifficulty usernameDifficulty:usernameDifficulties){
            String username = usernameDifficulty.getUsername();
            double difficulty = usernameDifficulty.getDifficulty();
            Date time = usernameDifficulty.getTime();
            String dateTimeStr = simplehour.format(time);
            String[] date = dateTimeStr.split(":");
            redisUtil.hset(coin.toUpperCase()+":MILL:ONEHOUR:" + username, date[1], difficulty);
        }
        //todo 测试
        Long updateDate = updateDateTime.getTime();
        //获取15分钟前的时间戳
        Long before15Min = updateDate - (15 * 60 * 1000);
        //获取30分钟前的时间戳
        Long before30Min = updateDate - (30 * 60 * 1000);
        //获取60分钟前的时间戳
        Long before60Min = updateDate - (60 * 60 * 1000);
        //转成时间
        Date before15MinTime = new Date(before15Min);
        Date before30MinTime = new Date(before30Min);
        Date before60MinTime = new Date(before60Min);
        log.info("before15MinTime:" + before15MinTime);
        log.info("before30MinTime:" + before30MinTime);
        log.info("before60MinTime:" + before60MinTime);
        log.info("updateDateTime:" + updateDateTime);
        //todo 遍历入缓存 3
        List<UsernameDifficulty> difficulty15s = shareService.getBytimeAndUsername(before15MinTime, updateDateTime);
        for (UsernameDifficulty userDifficulty : difficulty15s) {
            String username = userDifficulty.getUsername();
            Double difficulty = userDifficulty.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":MILL:STATE:" + username, "sumDifficulty15", difficulty, 7 * 24 * 60 * 60 * 1000);
        }
        List<UsernameDifficulty> difficulty30s = shareService.getBytimeAndUsername(before30MinTime, updateDateTime);
        for (UsernameDifficulty userDifficulty : difficulty30s) {
            String username = userDifficulty.getUsername();
            Double difficulty = userDifficulty.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":MILL:STATE:" + username, "sumDifficulty30", difficulty, 7 * 24 * 60 * 60 * 1000);
        }
        List<UsernameDifficulty> difficulty60s = shareService.getBytimeAndUsername(before60MinTime, updateDateTime);
        for (UsernameDifficulty userDifficulty : difficulty60s) {
            String username = userDifficulty.getUsername();
            Double difficulty = userDifficulty.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":MILL:STATE:" + username, "sumDifficulty60", difficulty, 7 * 24 * 60 * 60 * 1000);
        }
        //todo 遍历入缓存 3
        List<UsernameDifficulty> valid15s = shareService.getValidByTime(before15MinTime, updateDateTime);
        for (UsernameDifficulty userDifficulty : valid15s) {
            String username = userDifficulty.getUsername();
            Double difficulty = userDifficulty.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":MILL:STATE:" + username, "valid15", difficulty, 7 * 24 * 60 * 60 * 1000);
        }
        List<UsernameDifficulty> valid30s = shareService.getValidByTime(before30MinTime, updateDateTime);
        for (UsernameDifficulty userDifficulty : valid30s) {
            String username = userDifficulty.getUsername();
            Double difficulty = userDifficulty.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":MILL:STATE:" + username, "valid30", difficulty, 7 * 24 * 60 * 60 * 1000);
        }
        List<UsernameDifficulty> valid60s = shareService.getValidByTime(before60MinTime, updateDateTime);
        for (UsernameDifficulty userDifficulty : valid60s) {
            String username = userDifficulty.getUsername();
            Double difficulty = userDifficulty.getDifficulty();
            redisUtil.hset(coin.toUpperCase() + ":MILL:STATE:" + username, "valid60", difficulty, 7 * 24 * 60 * 60 * 1000);
        }

        for (UserNameTime usernameTime : usernames) {
            String username = usernameTime.getUsername();
            Date time = usernameTime.getTime();
            Long before = time.getTime();
            Long sec = new Date().getTime() - before;
            redisUtil.hset(coin.toUpperCase() + ":MILL:STATE:" + username, "lazy", sec, 7 * 24 * 60 * 60 * 1000);
            redisUtil.hset(coin.toUpperCase() + ":MILL:STATE:" + username, "time", simpleDate.format(updateDateTime), 7 * 24 * 60 * 60 * 1000);
        }
    }


    /**
     * 通过时间获取零点时间
     *
     * @param date
     * @return
     */
    Date getZeroTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一小时区间
     *
     * @param startTime
     * @return
     */
    public Map<String, Date> getOneHourTimeZore(Date startTime) {
        Map<String, Date> dataMapOneHour = new HashMap<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        System.err.println("hour:" + hour);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("开始时间：" + calendar.getTime());
        dataMapOneHour.put("startTime", calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, hour + 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("结束时间：" + calendar.getTime());
        dataMapOneHour.put("endTime", calendar.getTime());
        return dataMapOneHour;
    }

    /**
     * 根据当前时间每1小时减少开始区间
     *
     * @param startTime
     * @return
     */
    public Map<String, Date> getOneHourStartAndEndTime(Date startTime) {
        Map<String, Date> dataMapOneHour = new HashMap<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        System.err.println("hour:" + hour);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("开始时间：" + calendar.getTime());
        dataMapOneHour.put("startTime", calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        System.out.println("结束时间：" + calendar.getTime());
        dataMapOneHour.put("endTime", calendar.getTime());
        return dataMapOneHour;
    }

    /**
     * 根据当前时间每15分钟减少开始区间
     *
     * @param startTime
     * @return
     */
    public Map<String, Date> getStartAndEndTime(Date startTime) {
        Map<String, Date> dataMap15min = new HashMap<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startTime);
        int min = calendar.get(Calendar.MINUTE);
        System.err.println(min);
        if (min >= 45) {
            calendar.set(Calendar.MINUTE, 45);
        } else if (min >= 30) {
            calendar.set(Calendar.MINUTE, 30);
        } else if (min >= 15) {
            calendar.set(Calendar.MINUTE, 15);
        } else {
            calendar.set(Calendar.MINUTE, 0);
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println("开始时间：" + calendar.getTime());
        dataMap15min.put("startTime", calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        System.out.println("结束时间：" + calendar.getTime());
        dataMap15min.put("endTime", calendar.getTime());
        return dataMap15min;
    }
    //起始时间和终止时间计算(往前倒退24小时)
    public Map<String, Date> getStartWithEndTime() {
        Map<String, Date> map = new HashMap<>();
        Date time = new Date();
        map.put("endTime", time);
        //每天结束的时间
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(time);
        theCa.add(theCa.DATE, -1);//
        Date endDate = theCa.getTime();
        map.put("startTime", endDate);
        return map;
    }

    /*===========================setting start=========================*/
    private void update(String name, String value) {
        Integer rows = configMapper.update(name, value, new Date());
        if (rows != 1) {
            throw new UpdateException("修改配置时出现未知错误!");
        }
    }

    private String getValueByName(String name) {
        return configMapper.findValueByKey(name);
    }

    /*===========================setting end=========================*/

}




