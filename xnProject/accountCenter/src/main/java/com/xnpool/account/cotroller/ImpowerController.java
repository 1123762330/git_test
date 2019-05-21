package com.xnpool.account.cotroller;

import com.xnpool.account.entity.Authorinsing;
import com.xnpool.account.entity.ByAuthorising;
import com.xnpool.account.entity.Impower;
import com.xnpool.account.service.ImpowerService;
import com.xnpool.account.util.Resp;
import com.xnpool.account.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: zly
 * @Date: 2019/5/15 17:52
 */
@RestController
@RequestMapping("/authorising")
@Slf4j
public class ImpowerController extends BaskController {
    @Autowired
    private ImpowerService impowerService;

    /**
     * 添加授权
     *
     * @param impower
     * @return
     */
    @RequestMapping("addAuthorising")//accountId,time,userId,phone，
    public ResponseResult<Void> addAuthorising (Impower impower) {
        impower.setStartTime(new Date());
        String byImpowerNamepram = impower.getByImpowerName();
        Integer userId = impower.getUserId();
        //拿到从数据库查询返回的授权数据
        Impower impowerData = impowerService.selectStatu(impower);
        //获取传入的参数子账户Id
        int pramImpowerAid = impower.getImpowerAid();
        //获取传入的读写的权限是r还是w
        String authority = impower.getAuthority();
        //拿到从数据库查询返回的被授权方的用户名或者是手机号
        String byImpowerName = impower.getByImpowerName();
        if(impowerData!=null){
            //拿到从数据库查询返回的授权方子账户Id
            int impowerAid = impowerData.getImpowerAid();
            //两个作对比,如果相等则说明是二次授权,执行修改状态重新设置时间,否则就新增一条授权
            if (impowerAid == pramImpowerAid) {
                impower.setId(impowerData.getId());
                impowerService.updateAuthoris(impower);
            }
        }else {
            /// TODO: 2019/5/15 被授权方存不存在
            //将用户在页面上输入的被授权方去数据库查询,如果授权方存在,则授权,否则提醒用户被授权方不存在
            String users = impowerService.selectUsers(byImpowerName);
            if (users != null) {
                //如果授权方授权此账户数量已经超过5个,则无法再添加授权
                Integer count = impowerService.selectAuthorisingCount(pramImpowerAid);
                if (authority.equals("r") && count <= 5) {
                    String remark=new Date()+",8用户Id为"+userId+"授权子账户"+pramImpowerAid+"给"+byImpowerNamepram;
                    impower.setRemark(remark);
                    impowerService.addAuthorising(impower);
                } else {
                    log.info("查看授权已达到最大数量");
                }
                //如果授权方授权此账户管理的权限,并且数量超过2个,则无法添加管理权限
                if (authority.equals("w") && count <= 2) {
                    impowerService.addAuthorising(impower);
                } else {
                    log.info("管理授权已达到最大数量");
                }
            } else {
                log.info("被授权方不存在!");
            }
        }



        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 当授权时间结束,修改状态
     *
     * @param impowerAid
     * @return
     */
    @RequestMapping("updateAuthorising")
    public ResponseResult<Void> updateAuthorising (Integer impowerAid) {
        impowerService.updateAuthorising(impowerAid);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 被授权方显示显示所有自己接受授权账户列表
     *
     * @param byImpowerName
     * @return
     */
    @RequestMapping("selectByImpowerName")
    public Resp selectByImpowerName (String byImpowerName) {
        List<ByAuthorising> byImpowerNams = impowerService.selectByImpowerName(byImpowerName);
        return Resp.success(byImpowerNams);
    }

    /**
     * 授权方显示被授权的用户名及权限
     *
     * @param userId
     * @param impowerAid
     * @return
     */
    @PostMapping("show_by_authorising")
    public ResponseResult<Map<String, List<Authorinsing>>> selectByUserId (Integer userId, Integer impowerAid) {
        List<Authorinsing> authorinsings = impowerService.selectByUserId(userId, impowerAid);
        List<Authorinsing> readList = new ArrayList<>();
        List<Authorinsing> writeList = new ArrayList<>();
        Map<String, List<Authorinsing>> totalMap = new HashMap<>();
        authorinsings.forEach((authorinsing) -> {
            String author = authorinsing.getAuthority();
            if ("r".equals(author)) {
                readList.add(authorinsing);
            } else if ("w".equals(author)) {
                writeList.add(authorinsing);
            }
        });
        totalMap.put("readList", readList);
        totalMap.put("writeList", writeList);
        return new ResponseResult<>(SUCCESS, totalMap);
    }

}
