package tech.codestory.research.boot.service.impl;

import org.springframework.stereotype.Service;
import tech.codestory.research.boot.model.UserInfo;
import tech.codestory.research.boot.service.UserInfoSecondService;

import javax.transaction.Transactional;

@Service
public class UserInfoSecondServiceImpl implements UserInfoSecondService {
    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserInfo getUserInfo(String account) {
        return null;
    }
}
