package tech.codestory.research.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 创建一个 BeanPostProcessor ， 为了方便查看Spring 注册的 Bean
 *
 * @author liaojunyong
 */
@Component
@Slf4j
public class SpringBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (log.isInfoEnabled()) {
            log.info("注册Bean {} ： {}", beanName, bean.getClass().getName());
        }
        return bean;
    }
}
