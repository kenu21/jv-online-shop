package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class BuyController extends HttpServlet {

    @Inject
    static BucketService bucketService;

    @Inject
    static UserService userService;

    @Inject
    static OrderService orderService;

    private static final Long VASYA_ID = 0L;
    private static final Long BUCKED_ID_OF_VASYA = 0L;

    Bucket bucket = new Bucket(VASYA_ID);

    {
        bucketService.create(bucket);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String itemId = req.getParameter("items_id");
        bucketService.addItem(BUCKED_ID_OF_VASYA, Long.valueOf(itemId));

        resp.sendRedirect(req.getContextPath() + "/servlet/getallitems");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
