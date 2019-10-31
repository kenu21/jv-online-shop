package com.kenu.internetshop.service.impl;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.annotations.Service;

import com.kenu.internetshop.dao.OrderDao;
import com.kenu.internetshop.dao.UserDao;
import com.kenu.internetshop.model.Item;
import com.kenu.internetshop.model.Order;
import com.kenu.internetshop.model.User;
import com.kenu.internetshop.service.OrderService;

import java.util.ArrayList;
import java.util.List;

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
    public Order delete(Long id) {
        return orderDao.delete(id);
    }

    @Override
    public Order completeOrder(List items, Long userId) {
        List<Item> newItems = new ArrayList<>(items);
        Order order = new Order(userId, newItems);
        User user = userDao.get(userId);
        order.setUser(user);
        orderDao.create(order);
        return order;
    }

    @Override
    public List<Order> getAllOrdersForUser(Long userId) {
        return orderDao.getOrders(userId);
    }
}
