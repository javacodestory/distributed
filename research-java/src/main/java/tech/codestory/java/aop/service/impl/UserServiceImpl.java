package tech.codestory.java.aop.service.impl;

import tech.codestory.java.aop.service.UserService;

/**
 * 用户 Service 实现
 *
 * @author liaojunyong
 */
public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String account, String password) {
        System.out.println("实际调用 UserServiceImpl.login() 方法");
        return true;
    }
}
