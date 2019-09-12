package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.model.User;

public interface UserService {

    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List getOrders(Long userId);
}
