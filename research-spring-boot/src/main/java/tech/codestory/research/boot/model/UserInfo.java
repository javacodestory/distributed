package tech.codestory.research.boot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author javacodestory@gmail.com
 */
@Setter
@Getter
@ToString
public class UserInfo implements Serializable {

    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String name;
}
