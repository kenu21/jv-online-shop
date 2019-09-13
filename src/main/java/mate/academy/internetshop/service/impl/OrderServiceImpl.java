package mate.academy.internetshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.annotations.Service;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private static OrderDao orderDao;

    @Inject
    private static UserDao userDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public void delete(Long id) {
        orderDao.delete(id);
    }

    @Override
    public Order completeOrder(List items, Long userId) {
        List<Item> newItems = new ArrayList<>(items);
        Order order = new Order(userId, newItems);
        orderDao.create(order);
        userDao.get(userId).getOrders().add(order);
        return order;
    }

    @Override
    public List<Order> getAllOrdersForUser(Long userId) {
        return Storage.orders.stream()
                .filter(o -> o.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
