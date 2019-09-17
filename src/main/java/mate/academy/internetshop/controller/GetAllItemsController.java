package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;

public class GetAllItemsController extends HttpServlet {

    @Inject
    private static ItemService itemService;

    @Inject
    private static UserService userService;

    {
        Item nokia = new Item("Nokia", 30D);
        itemService.create(nokia);
        Item samsung = new Item("Samsung", 200D);
        itemService.create(samsung);
        Item iphone = new Item("iphone 11", 1000D);
        itemService.create(iphone);

        User vasya = new User("Vasya");
        userService.create(vasya);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        req.setAttribute("items", items);

        req.getRequestDispatcher("/WEB-INF/views/allitems.jsp").forward(req, resp);
    }
}
