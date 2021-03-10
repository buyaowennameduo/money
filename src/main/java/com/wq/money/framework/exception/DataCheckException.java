package com.wq.money.framework.exception;

public class DataCheckException extends BaseException {
    /**
     *
     */
    private static final long serialVersionUID = 95478418837203456L;

    public DataCheckException(String message) {
        super(message);
    }

    public DataCheckException(Throwable e) {
        super(e);
    }

    public DataCheckException(String message, Throwable e) {
        super(message, e);
    }

}
