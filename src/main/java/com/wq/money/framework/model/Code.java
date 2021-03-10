package com.wq.money.framework.model;

public final class Code {

    /* 成功 */
    public final static String $200_SUCCESS = "200"; // 请求成功
    /* 登录 */
    public final static String $1000_EXCEPTION = "1000"; // 系统错误
    public final static String $1001_AUTH_FAIL = "1001"; // 登录失败
    public final static String $1002_NO_AUTH = "1002"; // 没有权限
    /* 参数 */
    public final static String $1101_PARAMETER_ERROR = "1101"; // 参数错误
    /* 数据库 */
    public final static String $1201_SQL_ERROR = "1201"; // 数据库异常
    /* 文件 */
    public final static String $1301_FILE_ERROR = "1301"; // 文件异常
    /* ERP */
    public final static String $1401_FILE_ERROR = "1401"; // ERP接口调用异常
    /**
     * cookie校验失败
     */
    public final static String $1509_DATA_CHECK_ERROR = "1509"; // 数据校验异常
    /* 校验 */
    public final static String $1501_DATA_CHECK_ERROR = "1501"; // 数据校验异常
    /* 工作流 */
    public final static String $1501_WORK_FLOW_ERROR = "1601"; // 工作流异常
}
