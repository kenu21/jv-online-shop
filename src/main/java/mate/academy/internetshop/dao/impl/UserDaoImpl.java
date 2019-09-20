package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.User;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        Storage.users.add(user);
        return user;
    }

    @Override
    public User get(Long userId) {
        return Storage.users
                .stream().filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find user with id " + userId));
    }

    @Override
    public User update(User user) {
        for (int i = 0; i < Storage.users.size(); i++) {
            if (Storage.users.get(i).getId().equals(user.getId())) {
                Storage.users.set(i, user);
                return user;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public User delete(Long id) {
        User user = get(id);
        Storage.users.removeIf((u) -> u.getId().equals(id));
        return user;
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

    @Override
    public User login(String login, String psw) throws AuthenticationException {
        Optional<User> user = Storage.users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
        if (user.isEmpty() || !user.get().getPassword().equals(psw)) {
            throw new AuthenticationException("incorrect username or password");
        }
        return user.get();
    }

    @Override
    public Optional<User> getByToken(String token) {
        return Storage.users.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
