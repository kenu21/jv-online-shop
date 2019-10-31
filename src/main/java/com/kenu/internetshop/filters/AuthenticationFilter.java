package com.kenu.internetshop.filters;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.model.User;
import com.kenu.internetshop.service.UserService;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class AuthenticationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthenticationFilter.class);
    private static final String COOKIE = "KENU";

    @Inject
    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (req.getCookies() == null) {
            processUnAuthenticated(req, resp);
            return;
        }

        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals(COOKIE)) {
                Optional<User> user = userService.getByToken(cookie.getValue());
                if (user.isPresent()) {
                    logger.info("User " + user.get().getLogin() + " was authenticated");
                    chain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }
        logger.info("User was not authenticated");
        processUnAuthenticated(req, resp);
    }

    private void processUnAuthenticated(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    @Override
    public void destroy() {}
}
