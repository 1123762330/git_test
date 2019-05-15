package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 *汇总数据
 */
public interface SummarizedData {
    void sumDate(String cion) throws ParseException;
    public Map<String,Date> getOneHourStartAndEndTime(Date startTime);
}
