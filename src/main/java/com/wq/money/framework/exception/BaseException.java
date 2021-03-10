package com.wq.money.framework.exception;

public class BaseException extends RuntimeException{
    private String errorMsg;
    public BaseException(String message) {
        super(message);
    }
    public BaseException(String message,String errorMsg) {
        super(message);
        this.errorMsg =errorMsg;
    }

    public BaseException(Throwable e) {
        super(e);
    }
    public BaseException(Throwable e,String errorMsg) {
        super(e);
        this.errorMsg =errorMsg;
    }


    public BaseException(String message, Throwable e) {
        super(message, e);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
