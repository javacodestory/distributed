package tech.codestory.java.aop.service;

/**
 * 用户 Service 接口
 *
 * @author liaojunyong
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param account
     * @param password
     * @return
     */
    boolean login(String account, String password);
}
