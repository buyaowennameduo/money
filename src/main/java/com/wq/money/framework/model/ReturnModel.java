package com.wq.money.framework.model;


public class ReturnModel<T> {
    private String status;
    private String code;
    private T data;
    private String message;
    private Object children;
    private boolean success;

    private static ReturnModel newInstance() {
        return new ReturnModel();
    }

    public static ReturnModel newSuccessInstance() {
        ReturnModel model = newInstance();
        model.setStatus(Status.SUCCESS);
        model.setCode(Code.$200_SUCCESS);
        model.setSuccess(true);
        return model;
    }

    public static <T> ReturnModel newSuccessInstance(T data) {
        ReturnModel model = newSuccessInstance();
        model.setData(data);
        return model;
    }

    public static<T> ReturnModel newSuccessInstance(T data, String message) {
        ReturnModel model = newInstance();
        model.setStatus(Status.SUCCESS);
        model.setData(data);
        model.setMessage(message);
        model.setSuccess(false);
        return model;
    }

    public static ReturnModel newFailureInstance(String code) {
        ReturnModel model = newInstance();
        model.setStatus(Status.FAILURE);
        model.setCode(code);
        model.setSuccess(false);
        return model;
    }

    public static ReturnModel newFailureInstance(String code, Object data) {
        ReturnModel model = newInstance();
        model.setStatus(Status.FAILURE);
        model.setCode(code);
        model.setData(data);
        model.setSuccess(false);
        return model;
    }

    public static ReturnModel newFailureInstance(Integer code, Object data) {
        ReturnModel model = newInstance();
        model.setStatus(Status.FAILURE);
        model.setCode(String.valueOf(code));
        model.setData(data);
        model.setSuccess(false);
        return model;
    }

    public static ReturnModel newFailureInstance(String code, String message) {
        ReturnModel model = newInstance();
        model.setStatus(Status.FAILURE);
        model.setCode(code);
        model.setMessage(message);
        model.setSuccess(false);
        return model;
    }

    public static ReturnModel newFailureInstance(String code, Object data, String message) {
        ReturnModel model = newInstance();
        model.setStatus(Status.FAILURE);
        model.setCode(code);
        model.setData(data);
        model.setMessage(message);
        model.setSuccess(false);
        return model;
    }

    public static ReturnModel newInstance(String status, String code, Object data, String message) {
        ReturnModel model = newInstance();
        model.setStatus(status);
        model.setCode(code);
        model.setData(data);
        model.setMessage(message);
        model.setSuccess(true);
        return model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getChildren() {
        return children;
    }

    public void setChildren(Object children) {
        this.children = children;
    }
}

