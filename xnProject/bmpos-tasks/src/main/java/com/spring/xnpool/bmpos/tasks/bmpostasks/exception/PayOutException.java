package com.spring.xnpool.bmpos.tasks.bmpostasks.exception;

public class PayOutException extends ServiceException {
    public PayOutException() {
        super();
    }

    public PayOutException(String message) {
        super(message);
    }

    public PayOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayOutException(Throwable cause) {
        super(cause);
    }

    protected PayOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
