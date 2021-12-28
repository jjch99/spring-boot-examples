package org.example.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
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

    @Test
    public void getUser() {
        Long userId = 1547956106479L;
        User user = userService.getUser(userId);
        if (user != null) {
            log.info(new Gson().toJson(user));
        }
    }

}
