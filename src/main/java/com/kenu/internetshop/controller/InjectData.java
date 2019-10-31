package com.kenu.internetshop.controller;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.model.Bucket;
import com.kenu.internetshop.model.Item;
import com.kenu.internetshop.model.Role;
import com.kenu.internetshop.model.User;
import com.kenu.internetshop.service.BucketService;
import com.kenu.internetshop.service.ItemService;
import com.kenu.internetshop.service.UserService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectData extends HttpServlet {

    @Inject
    private static ItemService itemService;

    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Item nokia = new Item("nokia", 50D);
        itemService.create(nokia);

        Item samsung = new Item("samsung", 300D);
        itemService.create(samsung);

        Item iphone11 = new Item("iphone11", 1000D);
        itemService.create(iphone11);

        User vasya = new User("Vasya", "1", "1");
        vasya.addRole(Role.of("USER"));
        userService.create(vasya);

        User admin = new User("Hero", "2", "2");
        admin.addRole(Role.of("ADMIN"));
        userService.create(admin);

        Bucket bucket = new Bucket(vasya.getId());
        bucketService.create(bucket);

        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
