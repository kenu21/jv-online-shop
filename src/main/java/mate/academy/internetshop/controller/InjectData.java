package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;

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
        Item nokia = new Item("Nokia", 30D);
        itemService.create(nokia);
        Item samsung = new Item("Samsung", 200D);
        itemService.create(samsung);
        Item iphone = new Item("iphone 11", 1000D);
        itemService.create(iphone);

        User vasya = new User("Vasya", "1", "1");
        vasya.addRole(Role.of("USER"));
        userService.create(vasya);

        User admin = new User("Hero", "2", "2");
        admin.addRole(Role.of("ADMIN"));
        userService.create(admin);

        Bucket bucket = new Bucket(vasya.getId());

        bucketService.create(bucket);

        req.getRequestDispatcher("/WEB-INF/views/inject.jsp").forward(req, resp);
    }
}
