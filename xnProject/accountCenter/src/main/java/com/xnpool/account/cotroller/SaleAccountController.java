package com.xnpool.account.cotroller;

import com.xnpool.account.entity.SaleAccount;
import com.xnpool.account.entity.UsersAndCoins;
import com.xnpool.account.service.ISaleAccountService;
import com.xnpool.account.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/account")
public class SaleAccountController extends BaskController {
    @Autowired
    ISaleAccountService saleAccountService;
    /**
     * 根据地址和币种获取用户名
     * @param coin
     * @param address
     * @return
     */
    @GetMapping("/get_user")
    public ResponseResult<String> getUsernameByCoinAndAddress( String coin, String address){
         String data = saleAccountService.getUsernameByCoinAndAddress(coin,address);
         ResponseResult responseResult = new ResponseResult<>();
         responseResult.setState(SUCCESS);
         responseResult.setData(data);
        return responseResult;
    }
    /**
     * 新增账户
     * @param saleAccount
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(SaleAccount saleAccount){
        saleAccountService.add(saleAccount);
        return new ResponseResult<>(SUCCESS);
    }
    /**
     * 修改账户
     * @param saleAccount
     * @return
     */
    @PostMapping("/change")
    public ResponseResult<Void> change(SaleAccount saleAccount){
        saleAccountService.change(saleAccount);
        return new ResponseResult<>(SUCCESS,"您确定要修改吗?修改后将无法找到任何记录!");
    }
    /**
     * 查询用户所有子账户列表
     * @param
     * @return
     */
    @PostMapping("/get")
    public ResponseResult<List<SaleAccount>> getByUid(HttpSession session){
        Integer userId = getUidfromSession(session);
        List<SaleAccount> data = saleAccountService.getByUid(userId);
        return new ResponseResult<>(SUCCESS,data);
    }
    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ResponseResult<Void> dropByid(Integer id,HttpSession session){
        Integer userId = getUidfromSession(session);
        saleAccountService.dropByid(id,userId);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 查询子账户列表和币种列表
     * @return
     */
    @RequestMapping("/findUsersAndCoins")
    public ResponseResult<List<UsersAndCoins>>  selectUsersAndCoins(HttpSession session) {
        //Integer userId = getUidfromSession(session);
        Integer userId=1;
        List<UsersAndCoins> usersAndCoinsList = saleAccountService.selectUsersAndCoins(userId);
        return new ResponseResult<>(SUCCESS,usersAndCoinsList);
    }
    /**
     * 通过传入的币种和钱包地址判断有没有相应的数据
     * @param coin
     * @param address
     * @return
     */
    @GetMapping("/findDataByAddress")
    public ResponseResult<Integer> findDataByAddress(@RequestParam("coin") String coin,@RequestParam("address") String address) {
        Integer userId = saleAccountService.findDataByAddress(coin, address);
        
        return new ResponseResult<>(SUCCESS,userId);
    }

    /**
     * 通过用户名查询用户Id
     * @param usersName
     * @return
     */
    @GetMapping("/finduserIdByUsersName")
    public ResponseResult<Integer> finduserIdByUsersName(String usersName) {
        Integer userId=saleAccountService.selectUsersId(usersName);
        return new ResponseResult<>(SUCCESS,userId);
    }


}
