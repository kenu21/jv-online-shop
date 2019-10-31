package com.kenu.internetshop;

import com.kenu.internetshop.dao.BucketDao;
import com.kenu.internetshop.dao.ItemDao;
import com.kenu.internetshop.dao.OrderDao;
import com.kenu.internetshop.dao.UserDao;
import com.kenu.internetshop.dao.hibernate.BucketDaoHibernateImpl;
import com.kenu.internetshop.dao.hibernate.ItemDaoHibernateImpl;
import com.kenu.internetshop.dao.hibernate.OrderDaoHibernateImpl;
import com.kenu.internetshop.dao.hibernate.UserDaoHibernateImpl;
import com.kenu.internetshop.service.BucketService;
import com.kenu.internetshop.service.ItemService;
import com.kenu.internetshop.service.OrderService;
import com.kenu.internetshop.service.UserService;
import com.kenu.internetshop.service.impl.BucketServiceImpl;
import com.kenu.internetshop.service.impl.ItemServiceImpl;
import com.kenu.internetshop.service.impl.OrderServiceImpl;
import com.kenu.internetshop.service.impl.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class Factory {
    private static final Logger logger = Logger.getLogger(Factory.class);
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost/dbinternetshop?"
                            + "user=uniuser&password=12345&serverTimezone=UTC");
            logger.info("Connection with dbinternetshop is established!");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Can't establish connection to our DB!", e);
        }
    }

    private static ItemDao itemDao;
    private static BucketDao bucketDao;
    private static OrderDao orderDao;
    private static UserDao userDao;

    private static ItemService itemService;
    private static BucketService bucketService;
    private static OrderService orderService;
    private static UserService userService;

    public static ItemDao getItemDao() {
        if (itemDao == null) {
            itemDao = new ItemDaoHibernateImpl();
        }
        return itemDao;
    }

    public static BucketDao getBucketDao() {
        if (bucketDao == null) {
            bucketDao = new BucketDaoHibernateImpl();
        }
        return bucketDao;
    }

    public static OrderDao getOrderDao() {
        if (orderDao == null) {
            orderDao = new OrderDaoHibernateImpl();
        }
        return orderDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoHibernateImpl();
        }
        return userDao;
    }

    public static ItemService getItemService() {
        if (itemService == null) {
            itemService = new ItemServiceImpl();
        }
        return itemService;
    }

    public static BucketService getBucketService() {
        if (bucketService == null) {
            bucketService = new BucketServiceImpl();
        }
        return bucketService;
    }

    public static OrderService getOrderService() {
        if (orderService == null) {
            orderService = new OrderServiceImpl();
        }
        return orderService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
