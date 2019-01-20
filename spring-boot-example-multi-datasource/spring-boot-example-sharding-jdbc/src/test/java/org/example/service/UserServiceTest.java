package org.example.service;

import org.example.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void addUser() {

        User user = new User();
        user.setUserId(System.currentTimeMillis());
        user.setUserName("Tom");
        user.setStatus(Byte.valueOf("0"));
        userService.addUser(user);

    }

}
