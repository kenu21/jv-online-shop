package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.annotations.Inject;

import mate.academy.internetshop.service.OrderService;

public class DeleteOrderControler extends HttpServlet {

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("orders_id");
        orderService.delete(Long.parseLong(orderId));

        resp.sendRedirect(req.getContextPath() + "/servlet/getallorders");
    }
}
