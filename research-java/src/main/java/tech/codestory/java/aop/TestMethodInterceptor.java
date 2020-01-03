package tech.codestory.java.aop;

import tech.codestory.java.aop.service.RoleService;

/**
 * 测试 CGLIB 代理
 *
 * @author liaojunyong
 */
public class TestMethodInterceptor {
    public static void main(String[] args) {
        String roleCode = "super";
        CustomMethodInterceptor proxy = new CustomMethodInterceptor();
        // base为生成的增强过的目标类
        RoleService roleService = ServiceFactory.getInstance(proxy, RoleService.class);
        roleService.isValidRole(roleCode);
    }
}
