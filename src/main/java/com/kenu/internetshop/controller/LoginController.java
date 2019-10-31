package com.kenu.internetshop.controller;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.exceptions.AuthenticationException;
import com.kenu.internetshop.model.User;
import com.kenu.internetshop.service.UserService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
            Cookie cookie = new Cookie("KENU", user.getToken());
            resp.addCookie(cookie);

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());

            resp.sendRedirect(req.getContextPath() + "/index");
        } catch (AuthenticationException e) {
            logger.error("Authentication has failed " + "login = " + login);
            req.setAttribute("errorMsg", "Incorrect login or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
