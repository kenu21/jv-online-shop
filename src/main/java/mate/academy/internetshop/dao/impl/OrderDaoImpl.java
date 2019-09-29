package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.model.Order;

@Dao
public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.orders.add(order);
        return order;
    }

    @Override
    public Order get(Long orderId) {
        return Storage.orders
                .stream().filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find order with id " + orderId));
    }

    @Override
    public Order update(Order order) {
        for (int i = 0; i < Storage.orders.size(); i++) {
            if (Storage.orders.get(i).getId().equals(order.getId())) {
                Storage.orders.set(i, order);
                return order;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Order delete(Long id) {
        Order order = get(id);
        Storage.orders.removeIf((o) -> o.getId().equals(id));
        return order;
    }

    @Override
    public List<Order> getOrders(Long userId) {
        // don't use
        return null;
    }
}
