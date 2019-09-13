package mate.academy.internetshop.service.impl;

import java.util.List;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.annotations.Service;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public User create(User user) {
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
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public List getOrders(Long userId) {
        return userDao.get(userId).getOrders();
    }
}
