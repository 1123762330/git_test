package com.spring.xnpool.bmpos.tasks.bmpostasks.exception;

public class ActiveException extends ServiceException {

    private static final long serialVersionUID = 1872915151163879540L;

    public ActiveException() {
        super();
    }

    public ActiveException(String message) {
        super(message);
    }

    public ActiveException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActiveException(Throwable cause) {
        super(cause);
    }

    protected ActiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
