package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.daily;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DailyVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029272522025458091L;
	private Integer id;
	private Integer accountId;
	private String coinAddress;
	private BigDecimal confirmed;

	
	
}
