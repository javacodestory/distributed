package tech.codestory.java.aop.service;

/**
 * 这是一个 Service 实现，没有任何接口
 *
 * @author liaojunyong
 */
public class RoleService {
    /**
     * 是否是一个合法的 roleCode
     *
     * @param roleCode
     * @return
     */
    public boolean isValidRole(String roleCode) {
        System.out.println("实际调用 RoleService.isValidRole() 方法");
        return true;
    }
}
