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
    public ResponseResult<Void> changeName(String name, String oldName, String accountName,String coin) {
        millService.changeName(name, oldName, accountName,coin);
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
    public ResponseResult<Void> group(String accountName, Integer groupId, @RequestBody List<String> indexs,@RequestParam("userId") Integer userId) {
        if (indexs == null || indexs.size() <= 0) {
            return new ResponseResult(500, "集合参数为空");
        }
        millService.group(accountName, groupId, indexs,userId);
        return new ResponseResult<>(SUCCESS);
    }


    /**
     * 添加分组
     * @param groupName
     * @return
     */
    @RequestMapping("/addGroup")
    public ResponseResult<Void> addGroup(String groupName,Integer userId) {
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
    public ResponseResult<Void> delGroup(@RequestBody List<String> groupIds,@RequestParam("userId")Integer userId) {
        millService.delGroup(groupIds,userId);
        return new ResponseResult<>(SUCCESS,"删除成功");
    }

    /**
     * 查询子账户下所有的矿机
     * @param
     * @return
     */
    @RequestMapping("selectGroup")
    public ResponseResult<List<MillName>> selectGroup(Integer userId,String coin) {
        //System.err.println(userId);
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
    public ResponseResult<MillName> getNameByOldName(@RequestParam("oldName") String oldName,@RequestParam("coin") String coin){
         MillName name = millService.getNameByOldName(oldName,coin);
        return new ResponseResult<>(SUCCESS,name);
    }

    /**
     * 得到更改名
     * @param oldName
     * @return
     */
    @GetMapping("get_new_name")
    public ResponseResult<String>findNewName(@RequestParam("oldName") String oldName,@RequestParam("coin") String coin) {
        String newName = millService.findNewName(oldName,coin);
        ResponseResult result = new ResponseResult();
        result.setState(SUCCESS);
        result.setData(newName);
        return result;
    }
}
