package tech.codestory.research.boot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import tech.codestory.research.boot.model.UserInfo;
import tech.codestory.research.boot.service.UserInfoFirstService;
import tech.codestory.research.boot.service.UserInfoSecondService;
import tech.codestory.research.boot.service.UserInfoThirdService;
import tech.codestory.research.boot.service.impl.UserInfoFirstServiceImpl;
import tech.codestory.research.boot.service.impl.UserInfoService;
import tech.codestory.research.boot.thread.InheritableRemoteUserContainer;
import tech.codestory.research.boot.thread.RemoteUserContainer;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;

@Controller
@Slf4j
public class HomeController {
    @Autowired
    ApplicationContext context;
    @Autowired
    UserInfoFirstService userInfoService;

    @RequestMapping(value = {"", "/", "/index.html"})
    public ModelAndView home() {
        String name = "令狐冲";

        ModelAndView view = new ModelAndView("/index");
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount("account");
        userInfo.setName(name);
        userInfo.setPassword("password");
        log.info("userInfo:{}", userInfo);
        log.info("userInfo=", userInfo);

        RemoteUserContainer.setRemoteUser(name);
        InheritableRemoteUserContainer.setRemoteUser(name);

        for (int i = 0; i < 5; i++) {
            log.info("call service {} times start ...", i);
            log.info("remote user is {}", userInfoService.printRemoteUser());
            log.info("call service {} times complete", i);
        }

        return view;
    }

    @RequestMapping(value = {"/beans.json"})
    @ResponseBody
    public JSONArray beans() {
        Class[] beanClasses = new Class[]{UserInfoService.class, UserInfoFirstService.class, UserInfoFirstServiceImpl.class, UserInfoSecondService.class, UserInfoThirdService.class};
        JSONArray jsons = new JSONArray();

        for (Class beanClass : beanClasses) {
            Object bean = context.getBean(beanClass);
            JSONObject beanJson = new JSONObject();
            jsons.add(beanJson);
            beanJson.put("class", beanClass.getName());
            beanJson.put("bean", bean.getClass().getName());

            Class<?> getBeanClass = bean.getClass();
            AnnotatedType superClass = getBeanClass.getAnnotatedSuperclass();
            if (superClass != null) {
                beanJson.put("annotatedSuperclass", superClass);
            }

            AnnotatedType[] annotatedInterfaces = getBeanClass.getAnnotatedInterfaces();
            if (annotatedInterfaces != null && annotatedInterfaces.length > 0) {
                JSONArray interfaceJsons = new JSONArray();
                beanJson.put("annotatedInterfaces", interfaceJsons);
                for (AnnotatedType anInterface : annotatedInterfaces) {
                    JSONObject interfaceJson = new JSONObject();
                    interfaceJsons.add(interfaceJson);
                    interfaceJson.put("type", anInterface.getType());
                    if (Advised.class.getTypeName().equals(anInterface.getType().getTypeName())) {
                        log.info(anInterface.getType().toString());
                    }
                }
            }

            Type[] genericInterfaces = getBeanClass.getGenericInterfaces();
            if (genericInterfaces != null && genericInterfaces.length > 0) {
                JSONArray interfaceJsons = new JSONArray();
                beanJson.put("genericInterfaces", interfaceJsons);
                for (Type anInterface : genericInterfaces) {
                    interfaceJsons.add(anInterface);
                }
            }

            Type genericSuperclass = getBeanClass.getGenericSuperclass();
            if (genericSuperclass != null) {
                beanJson.put("genericSuperclass", genericSuperclass);
            }
        }
        return jsons;
    }
}
