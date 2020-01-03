package tech.codestory.java.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 自定义 InvocationHandler
 *
 * @author liaojunyong
 */
public class CustomInvocationHandler implements InvocationHandler {
    private Object targetObject;

    /**
     * 构造函数
     *
     * @param targetObject
     */
    CustomInvocationHandler(Object targetObject) {
        super();
        this.targetObject = targetObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        // 程序执行前加入逻辑，MethodBeforeAdviceInterceptor
        System.out.println("jdk proxy : 执行代码之前 ... ");

        // 程序执行
        Object result = method.invoke(targetObject, args);

        // 程序执行后加入逻辑，MethodAfterAdviceInterceptor
        System.out.println("jdk proxy : 执行代码之后");

        return result;
    }
}
