package tech.codestory.java.enums;

/**
 * @author junyongliao
 * @date 2019/9/2
 * @since 1.0.0
 */
public enum RetCodeEnum implements CodeValueBaseEnum {
    //@formatter:off
    OK(0, "OK"),
    SYSTEM_ERROR(-1, "系统错误"),
    DATABASE_ERROR(-10, "数据库异常"),
    REDIS_ERROR(-20, "Redis异常"),;
    //@formatter:on

    int code;
    String value;

    RetCodeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
