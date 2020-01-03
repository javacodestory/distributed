package tech.codestory.research.boot.service;

import tech.codestory.research.boot.model.UserInfo;

/**
 * 定义 Service 接口<br>
 * 这个接口的实现类没有任何添加任何其他注解
 *
 * @author liaojunyong
 */
public interface UserInfoFirstService {
    /**
     * 获取一个用户信息
     *
     * @param account
     * @return
     */
    UserInfo getUserInfo(String account);
}
