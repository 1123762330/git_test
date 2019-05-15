package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.payout;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TransactionsVO implements Serializable {
    private BigDecimal confirmed;
    private BigDecimal unconfirmed;
    private BigDecimal orphanded;
}
