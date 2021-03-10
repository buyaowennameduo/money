package com.wq.money.framework.exception;

public class IOException extends BaseException {

    private static final long serialVersionUID = 321915716375225853L;

    public IOException(String message) {
        super(message);
    }

    public IOException(Throwable e) {
        super(e);
    }

    public IOException(String message, Throwable e) {
        super(message, e);
    }
}
