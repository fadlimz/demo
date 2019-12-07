package com.fadli.demo.common.user.services;

import com.fadli.demo.base.exceptions.BusinessException;
import com.fadli.demo.base.parentClasses.BaseService;
import com.fadli.demo.common.user.models.User;
import com.fadli.demo.common.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<User> {

    @Autowired private UserRepository userRepository;

    @Override
    protected void initRepository() {
        repository = userRepository;
    }

    @Override
    protected void valMustExists(User entity) {
        User entityFromDb = findById(entity.getId());
        if (entityFromDb == null) {
            batchError("userService.user.not.found");
        }
    }

    @Override
    protected void valMustNotExists(User entity) {
        User entityFromDb = findByBk(entity.getUserCode());
        if (entityFromDb != null) {
            batchError("userService.userCode.already.exists", entity.getUserCode());
        }
    }

    public User findByBk(String userCode) {
        return userRepository.findByBk(userCode);
    }

    public List<User> getList() {
        return userRepository.getList();
    }

    @Override
    protected void setEditedValues(User entity, User entityFromDb) {
        entityFromDb.setUserName(entity.getUserName());
        entityFromDb.setPassword(entity.getPassword());
    }
}
