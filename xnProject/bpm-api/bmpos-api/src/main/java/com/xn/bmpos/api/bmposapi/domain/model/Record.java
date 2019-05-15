package com.xn.bmpos.api.bmposapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private String timestamp;//支付时间
    private BigDecimal amount;//收益
    private String address;//支付地址
    private String txid;//支付id

}
