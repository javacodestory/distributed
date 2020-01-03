package tech.codestory.research.boot.model;

import lombok.Data;

/**
 * 用户类
 *
 * @author liaojunyong
 */
@Data
public class UserInfo {
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
