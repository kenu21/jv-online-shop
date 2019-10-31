package com.kenu.internetshop.controller;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.service.BucketService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddItemToBucketController extends HttpServlet {

    @Inject
    static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        String itemId = req.getParameter("items_id");
        bucketService.addItem(bucketService.getByUserId(userId).getId(), Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/getallitems");
    }
}
