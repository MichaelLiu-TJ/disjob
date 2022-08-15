package liuwei.job.core.common;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

public class Response<T> {
    private int code;

    private String subCode;

    private String message;

    private T value;

    public Response() {
    }

    public static Response SUCCESS_RESPONSE = new Response<>(StatusCode.SUCCESSFUL.getCode(), StatusCode.SUCCESSFUL.getMsg());

    public static <T> Response<T> successNoData() {
        return new Response<>(StatusCode.SUCCESSFUL_NO_DATA.getCode(), StatusCode.SUCCESSFUL_NO_DATA.getMsg());
    }

    public static <T> Response<T> success(T value) {
        if (value == null) {
            return successNoData();
        }
        if (value instanceof Collection) {
            Collection collection = (Collection) value;
            if (CollectionUtils.isEmpty(collection)) {
                return successNoData();
            }
        }
        if (value instanceof Map) {
            Map map = (Map) value;
            if (CollectionUtils.isEmpty(map)) {
                return successNoData();
            }
        }

        return new Response<>(StatusCode.SUCCESSFUL.getCode(), null, StatusCode.SUCCESSFUL.getMsg(), value);
    }

    public Response(StatusCode statusCode) {
        this(statusCode.getCode(), statusCode.getMsg());
    }

    public Response(int code, String message) {
        this(code, null, message);
    }

    public Response(int code, String subCode, String message) {
        this(code, subCode, message, null);
    }

    public Response(int code, String subCode, String message, T value) {
        this.code = code;
        this.subCode = subCode;
        this.message = message;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
