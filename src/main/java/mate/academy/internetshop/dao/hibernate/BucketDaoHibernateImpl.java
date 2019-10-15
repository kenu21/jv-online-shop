package mate.academy.internetshop.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class BucketDaoHibernateImpl implements BucketDao {
    private static final Logger logger = Logger.getLogger(BucketDaoHibernateImpl.class);

    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        Long bucketId = null;
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            bucketId = (Long) session.save(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't create Bucket " + bucket, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        bucket.setId(bucketId);
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = session.get(Bucket.class, bucketId);
            return bucket;
        }
    }

    @Override
    public Bucket update(Bucket bucket) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update Bucket " + bucket, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bucket;
    }

    @Override
    public Bucket delete(Long id) {
        Bucket bucket = get(id);
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(bucket);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete bucket with id " + id, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bucket;
    }

    @Override
    public Bucket getByUserId(Long userId) {
        Bucket bucket = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Bucket WHERE user_id=:userId");
            query.setParameter("userId", userId);
            bucket = (Bucket) query.uniqueResult();
        }
        if (bucket == null) {
            logger.error("Can't get bucket by id!");
            throw new RuntimeException("Can't get bucket by id!");
        }
        return bucket;
    }

    @Override
    public Boolean addItem(Long bucketId, Long itemId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Bucket bucket = get(bucketId);
            List<Item> list = bucket.getItems();
            Item item = itemDao.get(itemId);
            list.add(item);
            update(bucket);
            return true;
        } catch (Exception e) {
            logger.error("Can't add item to bucket ", e);
            return false;
        }
    }

    @Override
    public List<Item> getItems(Long bucketId) {
        return get(bucketId).getItems();
    }

    @Override
    public Boolean deleteItem(Long bucketId, Long itemId) {
        try {
            Bucket bucket = get(bucketId);
            List<Item> list = bucket.getItems();
            Item itemFromParameter = itemDao.get(itemId);
            for (Item item : list) {
                if (item.getId().equals(itemFromParameter.getId())) {
                    list.remove(item);
                    break;
                }
            }
            update(bucket);
            return true;
        } catch (Exception e) {
            logger.error("Can't delete item from bucket ", e);
            return false;
        }
    }

    @Override
    public Boolean clear(Long bucketId) {
        try {
            Bucket bucket = get(bucketId);
            List<Item> list = bucket.getItems();
            list = new ArrayList();
            update(bucket);
            return true;
        } catch (Exception e) {
            logger.error("Can't clear bucket ", e);
            return false;
        }
    }
}
