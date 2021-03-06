package com.xnpool.account.cotroller;

import com.xnpool.account.entity.SaleAccountVO;
import com.xnpool.account.entity.SaleAddress;
import com.xnpool.account.service.ISaleAddressService;
import com.xnpool.account.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/address")
public class SaleAddressController extends BaskController {
    @Autowired
    ISaleAddressService saleAddressService;
    /**
     * 添加地址
     * @param
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Void> addAddress(@RequestParam("accountId") Integer accountId, @RequestParam("currency") String currency, @RequestParam("coinAddress") String coinAddress){
        saleAddressService.add(accountId,currency,coinAddress);
        return new ResponseResult<>(SUCCESS);
    }

    /**
     * 修改地址
     * @param
     * @return
     */
    @PostMapping("/change")
    public ResponseResult<Void> changeAdress(Integer id, String coinAddress,Integer userId){
        saleAddressService.change(id,coinAddress,userId);
        return new ResponseResult<>(SUCCESS);
    }
    /**
     * 查询子账户下所有绑定钱包信息列表
     * @param accountId
     * @return
     */
    @GetMapping("/get_aid")
    public ResponseResult<List<SaleAddress>> getByAccountId(Integer accountId,Integer userId){
        List<SaleAddress> data = saleAddressService.getByAccountId(accountId,userId);
        return new ResponseResult<>(SUCCESS,data);
    }
    /**
     * 查询子账户下指定币种的钱包信息
     * @param name
     * @param coin
     * @return
     */
    @PostMapping("/get_ac")
    public ResponseResult<SaleAccountVO> getByAidAndCurrency(String name, String coin){
        SaleAccountVO data = saleAddressService.getByAidAndCurrency(name,coin);
        return new ResponseResult<>(SUCCESS,data);
    }
    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    @PostMapping("/drop")
    public ResponseResult<Void> dropByid(Integer id,Integer userId){
        saleAddressService.dropByid(id,userId);
        return new ResponseResult<>(SUCCESS);
    }
}
