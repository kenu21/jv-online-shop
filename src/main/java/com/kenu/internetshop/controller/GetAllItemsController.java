package com.kenu.internetshop.controller;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.model.Item;
import com.kenu.internetshop.service.ItemService;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllItemsController extends HttpServlet {

    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        req.setAttribute("items", items);

        req.getRequestDispatcher("/WEB-INF/views/allitems.jsp").forward(req, resp);
    }
}
