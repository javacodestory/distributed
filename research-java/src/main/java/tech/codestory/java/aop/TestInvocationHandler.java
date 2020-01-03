package tech.codestory.java.aop;

import tech.codestory.java.aop.service.UserService;
import tech.codestory.java.aop.service.impl.UserServiceImpl;

import java.lang.reflect.Proxy;

/**
 * 测试 JDK 动态代理
 *
 * @author liaojunyong
 */
public class TestInvocationHandler {
    /**
     * 程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        String account = "user";
        String password = "password";
        UserService userService = new UserServiceImpl();
        CustomInvocationHandler handler = new CustomInvocationHandler(userService);

        // Proxy为InvocationHandler实现类动态创建一个符合某一接口的代理实例
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService
                .getClass().getClassLoader(), userService.getClass()
                .getInterfaces(), handler);
        // 由动态生成的代理对象来 userServiceProxy 代理执行程序，其中 userServiceProxy 符合 UserService 接口
        userServiceProxy.login(account, password);
    }
}
