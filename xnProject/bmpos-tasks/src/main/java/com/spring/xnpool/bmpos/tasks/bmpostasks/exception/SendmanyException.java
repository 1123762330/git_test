package com.spring.xnpool.bmpos.tasks.bmpostasks.exception;

public class SendmanyException extends ServiceException {
    private static final long serialVersionUID = -1671648156393095878L;

    public SendmanyException() {
        super();
    }

    public SendmanyException(String message) {
        super(message);
    }

    public SendmanyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendmanyException(Throwable cause) {
        super(cause);
    }

    protected SendmanyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
