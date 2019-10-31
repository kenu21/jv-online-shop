package com.kenu.internetshop.controller;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.model.Order;
import com.kenu.internetshop.service.OrderService;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllOrdersController extends HttpServlet {

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        List<Order> orders = orderService.getAllOrdersForUser(userId);
        req.setAttribute("orders", orders);

        req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
    }
}
