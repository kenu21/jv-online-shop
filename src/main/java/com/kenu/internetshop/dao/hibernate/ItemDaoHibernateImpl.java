package com.kenu.internetshop.dao.hibernate;

import com.kenu.internetshop.annotations.Dao;
import com.kenu.internetshop.dao.ItemDao;
import com.kenu.internetshop.model.Item;
import com.kenu.internetshop.util.HibernateUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class ItemDaoHibernateImpl implements ItemDao {
    private static final Logger logger = Logger.getLogger(ItemDaoHibernateImpl.class);

    @Override
    public Item create(Item item) {
        Long itemId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            itemId = (Long) session.save(item);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't create Item " + item, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        item.setId(itemId);
        return item;
    }

    @Override
    public Item get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Item item = session.get(Item.class, id);
            return item;
        }
    }

    @Override
    public Item update(Item item) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update Item " + item, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return item;
    }

    @Override
    public Item delete(Long id) {
        Item item = get(id);
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(item);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete item with id " + id, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Item").list();
        }
    }
}
