package com.kenu.internetshop.dao;

import com.kenu.internetshop.model.Order;

import java.util.List;

public interface OrderDao {

    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    Order delete(Long id);

    List<Order> getOrders(Long userId);
}
