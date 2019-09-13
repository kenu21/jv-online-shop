package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.Order;

public interface OrderDao {

    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    Order delete(Long id);
}
