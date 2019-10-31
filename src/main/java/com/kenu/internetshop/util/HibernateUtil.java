package com.kenu.internetshop.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final Logger logger = Logger.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = initSessionFactory();

    private static SessionFactory initSessionFactory() {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            logger.info("Session factory is create.");
            return sessionFactory;
        } catch (Exception e) {
            logger.error("Can't create session factory!", e);
            throw new RuntimeException(e);
        }
    }

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
