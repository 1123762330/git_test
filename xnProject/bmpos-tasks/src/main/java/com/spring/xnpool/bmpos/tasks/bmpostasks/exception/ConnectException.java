package com.spring.xnpool.bmpos.tasks.bmpostasks.exception;

public class ConnectException extends ServiceException {
    private static final long serialVersionUID = 7741581945692300017L;

    public ConnectException() {
        super();
    }

    public ConnectException(String message) {
        super(message);
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectException(Throwable cause) {
        super(cause);
    }

    protected ConnectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
