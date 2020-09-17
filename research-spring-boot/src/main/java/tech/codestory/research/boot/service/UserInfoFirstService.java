package tech.codestory.research.boot.service;

import tech.codestory.research.boot.model.UserInfo;

/**
 * 没有添加其他注解的实现类
 *
 * @author javacodestory@gmail.com
 */
public interface UserInfoFirstService {
    /**
     * 获取一个用户信息
     *
     * @param account
     * @return
     */
    UserInfo getUserInfo(String account);

    /**
     * 打印用户信息
     */
    String printRemoteUser();
}
