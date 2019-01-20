package org.example.service;

import org.example.dao.UserMapper;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
        userMapper.insertSelective(user);
    }

    public User getUser(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

}
