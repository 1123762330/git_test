package com.xnpool.account.cotroller;

import com.xnpool.account.entity.MillName;
import com.xnpool.account.service.ISaleAccountService;
import com.xnpool.account.service.MillService;
import com.xnpool.account.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.NewThreadAction;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/mill")
public class MillController extends BaskController {
    @Autowired
    private MillService millService;
    @Autowired
    ISaleAccountService saleAccountService;
    @GetMapping("/add")
    public ResponseResult<Void> setMill(String coin) {
        millService.setMill(coin);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 改名
     * @param name
     * @param oldName
     * @param accountName
     * @return
     */
    @GetMapping("/change")
    public ResponseResult<Void> changeName(String name, String oldName, String accountName) {
        millService.changeName(name, oldName, accountName);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     *移动矿机
     * @param accountName
     * @param groupId
     * @param indexs
     * @return
     */
    @RequestMapping(value = "/group", method = RequestMethod.POST, produces = "application/json")
    public ResponseResult<Void> group(String accountName, Integer groupId, @RequestBody List<Integer> indexs) {
        if (indexs == null || indexs.size() <= 0) {
            return new ResponseResult(500, "集合参数为空");
        }
        millService.group(accountName, groupId, indexs);
        return new ResponseResult<>(SUCCESS);
    }


    /**
     * 添加分组
     * @param groupName
     * @return
     */
    @RequestMapping("/addGroup")
    public ResponseResult<Void> addGroup(String groupName,HttpSession session) {
        //通过用户名拿到UserId
        //Integer userId=saleAccountService.selectUsersId(usersName);
        Integer userId = getUidfromSession(session);
        List<String> groupNameList = millService.findGroupName(userId);
        if(groupNameList.contains(groupName)){
            return new ResponseResult<>(400,"分组名重复");
        }
        millService.addGroup(groupName,userId);
        return new ResponseResult<>(SUCCESS,"添加成功");
    }
    /**
     * 删除分组(软删除)
     * @param groupIds
     * @return
     */
    @RequestMapping(value = "/delGroup", method = RequestMethod.POST, produces = "application/json")
    public ResponseResult<Void> delGroup(@RequestBody List<Integer> groupIds) {
        millService.delGroup(groupIds);
        return new ResponseResult<>(SUCCESS,"删除成功");
    }

    /**
     * 查询子账户下所有的矿机
     * @param
     * @return
     */
    @RequestMapping("selectGroup")
    public ResponseResult<List<MillName>> selectGroup(HttpSession session,String coin) {
        //通过用户名拿到UserId
        Integer userId = getUidfromSession(session);
        System.err.println(userId);
        List<MillName> millNames = millService.selectGroup(userId,coin);
        return new ResponseResult<>(SUCCESS,millNames);
    }

    /**
     * 查询矿机分组
     * @param groupId
     * @return
     */
    @RequestMapping("selectGroupId")
    public ResponseResult<List<MillName>> selectGroupId(Integer groupId) {
        List<MillName> millNames = millService.selectGroupId(groupId);
        return new ResponseResult<>(SUCCESS,millNames);
    }

    /**
     * 通过原矿机名查询新名
     * @param oldName
     * @param coin
     * @return
     */
    @GetMapping("get_name")
    public ResponseResult<String> getNameByOldName(@RequestParam("oldName") String oldName,@RequestParam("coin") String coin){
         MillName name = millService.getNameByOldName(oldName,coin);
         ResponseResult result = new ResponseResult();
         result.setState(SUCCESS);
         result.setData(name);
        return result;
    }

}
