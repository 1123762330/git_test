package com.spring.xnpool.bmpos.tasks.bmpostasks.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.log.LoggerMessage;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.LoggerQueue;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;

@Service
public class LogFilter extends Filter<ILoggingEvent>{
    @Override
    public FilterReply decide(ILoggingEvent event) {
        LoggerMessage loggerMessage = new LoggerMessage(
                event.getMessage()
                , DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(),
                event.getLoggerName(),
                event.getLevel().levelStr
        );
        LoggerQueue.getInstance().push(loggerMessage);
        return FilterReply.ACCEPT;

    }
}
