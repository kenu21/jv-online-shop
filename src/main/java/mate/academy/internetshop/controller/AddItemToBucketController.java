package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class AddItemToBucketController extends HttpServlet {

    @Inject
    static BucketService bucketService;

    @Inject
    static UserService userService;

    @Inject
    static OrderService orderService;

    private static final Long USER_ID = 0L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String itemId = req.getParameter("items_id");
        bucketService.addItem(bucketService.get(USER_ID).getId(), Long.valueOf(itemId));

        resp.sendRedirect(req.getContextPath() + "/servlet/getallitems");
    }
}
