package com.kenu.internetshop.service;

import com.kenu.internetshop.model.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order get(Long id);

    Order update(Order item);

    Order delete(Long id);

    Order completeOrder(List items, Long userId);

    List<Order> getAllOrdersForUser(Long userId);

}
