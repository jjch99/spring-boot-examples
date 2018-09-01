package org.example.service;

import org.example.domain.User;

public interface UserServicce {

    User get(Long id);

    User add(User user);

    void update(User user);

    void delete(Long id);

    String hello(String hello);

}
