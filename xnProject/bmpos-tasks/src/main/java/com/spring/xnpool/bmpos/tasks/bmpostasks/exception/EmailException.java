package com.spring.xnpool.bmpos.tasks.bmpostasks.exception;

public class EmailException extends ServiceException {

    private static final long serialVersionUID = -2049838121003652293L;

    public EmailException() {
        super();
    }

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(Throwable cause) {
        super(cause);
    }

    protected EmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
