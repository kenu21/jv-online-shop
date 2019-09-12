package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.User;

public interface UserDao {

    User create(User userDao);

    User get(Long id);

    User update(User userDao);

    User delete(Long id);

}
