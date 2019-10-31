package com.kenu.internetshop.dao;

import com.kenu.internetshop.exceptions.AuthenticationException;
import com.kenu.internetshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User create(User userDao);

    User get(Long id);

    User update(User userDao);

    User delete(Long id);

    List<User> getAll();

    User login(String login, String psw) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
