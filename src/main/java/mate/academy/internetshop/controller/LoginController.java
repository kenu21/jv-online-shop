package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {

    @Inject
    private static UserService userService;

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String psw = req.getParameter("psw");
        try {
            logger.info("Authentication has starter...");
            User user = userService.login(login, psw);
            Cookie cookie = new Cookie("MATE", user.getToken());
            resp.addCookie(cookie);

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());

            resp.sendRedirect(req.getContextPath() + "/index");
        } catch (AuthenticationException e) {
            logger.info("Authentication has failed " + "login = " + login + "psw = " + psw);
            req.setAttribute("errorMsg", "Incorrect login or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
