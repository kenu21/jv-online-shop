package com.kenu.internetshop.controller;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.model.Item;
import com.kenu.internetshop.service.BucketService;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BucketControler extends HttpServlet {

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        List<Item> allOrdersForUser = bucketService.getAllItems(
                bucketService.getByUserId(userId).getId());
        req.setAttribute("orders", allOrdersForUser);

        req.getRequestDispatcher("/WEB-INF/views/bucket.jsp").forward(req, resp);
    }
}
