package com.spring.xnpool.bmpos.tasks.bmpostasks.exception;

public class ErrorMessageException extends ServiceException {

    private static final long serialVersionUID = 1514925356564683238L;

    public ErrorMessageException() {
        super();
    }

    public ErrorMessageException(String message) {
        super(message);
    }

    public ErrorMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorMessageException(Throwable cause) {
        super(cause);
    }

    protected ErrorMessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
