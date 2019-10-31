package com.kenu.internetshop.service.impl;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.annotations.Service;
import com.kenu.internetshop.dao.UserDao;
import com.kenu.internetshop.exceptions.AuthenticationException;
import com.kenu.internetshop.model.User;
import com.kenu.internetshop.service.UserService;
import com.kenu.internetshop.util.HashUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {
        user.setToken(getToken());
        user.setSalt(HashUtil.createSalt());
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id);
    }

    @Override
    public User update(User item) {
        return userDao.update(item);
    }

    @Override
    public User delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User login(String login, String psw) throws AuthenticationException {
        return userDao.login(login, psw);
    }

    @Override
    public Optional<User> getByToken(String token) {
        return userDao.getByToken(token);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }
}
