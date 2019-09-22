package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.User;

public interface UserDao {

    User create(User userDao);

    User get(Long id);

    User update(User userDao);

    User delete(Long id);

    List<User> getAll();

    User login(String login, String psw) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
