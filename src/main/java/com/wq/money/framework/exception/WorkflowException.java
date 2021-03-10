package com.wq.money.framework.exception;

public class WorkflowException extends BaseException {
    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(Throwable e) {
        super(e);
    }

    public WorkflowException(String message, Throwable e) {
        super(message, e);
    }
}
