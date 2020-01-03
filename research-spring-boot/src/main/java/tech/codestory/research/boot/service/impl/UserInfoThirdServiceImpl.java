package tech.codestory.research.boot.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import tech.codestory.research.boot.model.UserInfo;
import tech.codestory.research.boot.service.UserInfoSecondService;
import tech.codestory.research.boot.service.UserInfoThirdService;

import javax.persistence.Cacheable;
import javax.transaction.Transactional;

@Service
@Cacheable
public class UserInfoThirdServiceImpl implements UserInfoThirdService {
    @Override
    @Transactional(rollbackOn = Exception.class)
    @CachePut
    public UserInfo getUserInfo(String account) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    @CacheEvict
    public UserInfo saveUserInfo(UserInfo userInfo) {
        return null;
    }
}
