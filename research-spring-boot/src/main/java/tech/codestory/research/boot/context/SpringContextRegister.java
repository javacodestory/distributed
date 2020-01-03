package tech.codestory.research.boot.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringContextRegister implements ApplicationContextAware {
    private static ApplicationContext theApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.debug("ApplicationContext registered-->{}", applicationContext);
        theApplicationContext = applicationContext;
    }

    /**
     * 获取ApplicationContext实例
     *
     * @return
     */
    public ApplicationContext getApplicationContext() {
        return theApplicationContext;
    }

    /**
     * 日志中打印所有的Bean
     */
    public void infoBeans() {
        String[] beanDefinitionNames = theApplicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            log.info("bean name : {} , bean instance : {}", beanDefinitionName, theApplicationContext.getBean(beanDefinitionName).getClass().getName());
        }
    }
}
