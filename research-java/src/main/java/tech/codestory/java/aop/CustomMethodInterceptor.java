package tech.codestory.java.aop;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib 代理
 *
 * @author liaojunyong
 */
public class CustomMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object targetObject, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        // 在目标类代码执行之前，即为MethodBeforeAdviceInterceptor。
        System.out.println("cglib proxy : 执行代码之前 ... ");

        proxy.invokeSuper(targetObject, args);

        // 在目标类代码执行之后，即为MethodAfterAdviceInterceptor。
        System.out.println("cglib proxy : 执行代码之后");
        return null;
    }
}
