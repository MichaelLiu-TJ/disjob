package liuwei.job.core.common;

public enum StatusCode {

    //The following error codes represent successful access
    SUCCESSFUL(100000, "successful"),
    SUCCESSFUL_NO_DATA(100001, "successful no data"),
    SUCCESSFUL_WITH_DEFAULT_VALUE(100002, "successful with default value"),
    PARTIAL_SUCCESS(100003, "partial success"),

    //The following error codes represent failed access
    AUTHORIZATION_ERROR(200000, "authorization error"),

    LOGIN_SUCCESS(200001, "Login Success"),
    LOGIN_FAILED_DUO_TO_U_P(200002, "Incorrect username or password"),
    LOGIN_FAILED_SERVER_ERROR(200003, "Login failed due to server error"),

    LOGOUT_SUCCESS(200004, "Logout success"),
    LOGOUT_FAILED(200005, "Logout failed"),

    INVALID_SIGN(200010, "invalid signature"),
    PARAMETERS_MISSING(400001, "parameters missing"),
    INVALID_PARAMETERS(400002, "invalid parameters"),
    INTERNAL_ERROR(400003, "internal error"),

    BUSINESS_ERROR(500001, "business error"),
    USER_NOT_FOUND(500002, "User not found"),


    REGISTER_CLIENT_FAILED(500003, "Register Client Failed"),
    REGISTER_JOB_FAILED(500004, "Register Job Failed"),

    REGISTER_CLIENT_JOB_FAILED_NO_CLIENT_SERIAL(500010, "No client serial string."),
    REGISTER_CLIENT_JOB_FAILED_NOT_FIND_CLIENT(500011, "Can not find client by serial no."),
    REGISTER_CLIENT_JOB_FAILED_NOT_FIND_JOB(500012, "Can not find job by job id."),
    REGISTER_CLIENT_JOB_FAILED(500013, "Register client job relation failed."),

    FUNCTION_NOT_IMPLEMENTATION(999999, "Register client job relation failed."),
    ;


    private int code;

    private String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static StatusCode getByCode(int code) {
        for (StatusCode statusCode : StatusCode.values()) {
            if (statusCode.code == code) {
                return statusCode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "[" + code + "-" + msg + "]";
    }
}
