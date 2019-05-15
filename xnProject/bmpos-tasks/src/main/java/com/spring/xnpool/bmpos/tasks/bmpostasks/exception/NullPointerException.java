package com.spring.xnpool.bmpos.tasks.bmpostasks.exception;

public class NullPointerException extends ServiceException {

    private static final long serialVersionUID = -3679732286379753427L;

    public NullPointerException() {
        super();
    }

    public NullPointerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NullPointerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullPointerException(String message) {
        super(message);
    }

    public NullPointerException(Throwable cause) {
        super(cause);
    }
}
