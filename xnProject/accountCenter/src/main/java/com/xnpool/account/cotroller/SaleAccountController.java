package com.xnpool.account.cotroller;

import com.xnpool.account.entity.IsAccount;
import com.xnpool.account.entity.SaleAccount;
import com.xnpool.account.entity.UsersAndCoins;
import com.xnpool.account.mappers.SaleAccountMapper;
import com.xnpool.account.service.ISaleAccountService;
import com.xnpool.account.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> add(@RequestParam("name") String name, @RequestParam("remarkName") String remarkName,@RequestParam(value = "password",required=false) String password,Integer userId){
        SaleAccount saleAccount = new SaleAccount();
        saleAccount.setName(name);
        saleAccount.setRemarkName(remarkName);
        if(password==null){
            saleAccount.setPassword("123456");
        }
        saleAccount.setUserId(userId);
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
        return new ResponseResult<>(SUCCESS);
    }
    /**
     * 查询用户所有子账户列表
     * @param
     * @return
     */
    @PostMapping("/get")
    public ResponseResult<List<SaleAccount>> getByUid(@RequestParam("userId") Integer userId){
        List<SaleAccount> data = saleAccountService.getByUid(userId);
        return new ResponseResult<>(SUCCESS,data);
    }
    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ResponseResult<Void> dropByidAC(Integer id,Integer userId){
        saleAccountService.dropByid(id,userId);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 查询子账户列表和币种列表
     * @return
     */
    @RequestMapping("/findUsersAndCoins")
    public ResponseResult<List<UsersAndCoins>>  selectUsersAndCoins(Integer userId) {
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
    public ResponseResult<IsAccount> findDataByAddress(@RequestParam("coin") String coin,@RequestParam("address") String address) {
        IsAccount isAccount = saleAccountService.findDataByAddress(coin, address);

        return new ResponseResult<>(SUCCESS,isAccount);
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

    /**
     * 查询子账户
     * @param userId
     * @return
     */
    @GetMapping("/get_account_name")
      public ResponseResult<List<UsersAndCoins>> selectAccountName(Integer userId){
        List<UsersAndCoins> usersAndCoins = saleAccountService.selectAccountName(userId);
        return new ResponseResult<>(SUCCESS,usersAndCoins);
      }
}
