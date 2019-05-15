package com.spring.xnpool.bmpos.tasks.bmpostasks.exception;

public class NullDataException extends ServiceException {
    private static final long serialVersionUID = 5744887857423048069L;

    public NullDataException() {
        super();
    }

    public NullDataException(String message) {
        super(message);
    }

    public NullDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullDataException(Throwable cause) {
        super(cause);
    }

    protected NullDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
