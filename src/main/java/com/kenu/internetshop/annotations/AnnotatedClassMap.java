package com.kenu.internetshop.annotations;

import com.kenu.internetshop.Factory;
import com.kenu.internetshop.dao.BucketDao;
import com.kenu.internetshop.dao.ItemDao;
import com.kenu.internetshop.dao.OrderDao;
import com.kenu.internetshop.dao.UserDao;
import com.kenu.internetshop.service.BucketService;
import com.kenu.internetshop.service.ItemService;
import com.kenu.internetshop.service.OrderService;
import com.kenu.internetshop.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class AnnotatedClassMap {
    private static final Map<Class, Object> classMap = new HashMap();

    static {
        classMap.put(ItemDao.class, Factory.getItemDao());
        classMap.put(BucketDao.class, Factory.getBucketDao());
        classMap.put(OrderDao.class, Factory.getOrderDao());
        classMap.put(UserDao.class, Factory.getUserDao());
        classMap.put(ItemService.class, Factory.getItemService());
        classMap.put(BucketService.class, Factory.getBucketService());
        classMap.put(OrderService.class, Factory.getOrderService());
        classMap.put(UserService.class, Factory.getUserService());
    }

    public static Object getImplementation(Class interfaceClass) {
        return classMap.get(interfaceClass);
    }
}
