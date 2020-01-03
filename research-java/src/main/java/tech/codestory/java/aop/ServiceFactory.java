package tech.codestory.java.aop;

import org.springframework.cglib.proxy.Enhancer;

/**
 * 实现一个Service 工厂类，使用 Enhancer 创建service对象
 *
 * @author liaojunyong
 */
public class ServiceFactory {
    /**
     * 获得增强之后的目标类，即添加了切入逻辑advice之后的目标类
     *
     * @param proxy
     * @return
     */
    public static <T> T getInstance(CustomMethodInterceptor proxy, Class<T> tClass) {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(tClass);
        // 回调方法的参数为代理类对象CglibProxy，
        // 最后增强目标类调用的是代理类对象CglibProxy中的intercept方法
        enhancer.setCallback(proxy);

        // 此刻，base不是单纯的目标类，而是增强过的目标类
        T bean = (T) enhancer.create();
        return bean;
    }
}
