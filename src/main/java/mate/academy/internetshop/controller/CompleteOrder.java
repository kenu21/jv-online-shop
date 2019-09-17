package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;

public class CompleteOrder extends HttpServlet {

    @Inject
    private static BucketService bucketService;

    @Inject
    private static OrderService orderService;

    private static final Long VASYA_ID = 0L;
    private static final Long BUCKED_ID_OF_VASYA = 0L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> items = bucketService.getAllItems(BUCKED_ID_OF_VASYA);
        orderService.completeOrder(items, VASYA_ID);
        bucketService.clear(BUCKED_ID_OF_VASYA);

        resp.sendRedirect(req.getContextPath() + "/servlet/getallorders");
    }
}
