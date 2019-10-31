package com.kenu.internetshop.controller;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.model.Item;
import com.kenu.internetshop.service.BucketService;
import com.kenu.internetshop.service.OrderService;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompleteOrder extends HttpServlet {

    @Inject
    private static BucketService bucketService;

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        List<Item> items = bucketService.getAllItems(bucketService.getByUserId(userId).getId());
        orderService.completeOrder(items, userId);
        bucketService.clear(bucketService.getByUserId(userId).getId());

        resp.sendRedirect(req.getContextPath() + "/servlet/getallorders");
    }
}
