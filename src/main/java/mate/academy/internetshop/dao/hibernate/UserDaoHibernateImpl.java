package mate.academy.internetshop.dao.hibernate;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.HashUtil;
import mate.academy.internetshop.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class);

    @Inject
    private static BucketDao bucketDao;

    @Override
    public User create(User userDao) {
        Long userId = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hashPsw = HashUtil.hashPassword(userDao.getPassword(), userDao.getSalt());
            userDao.setPassword(hashPsw);
            userId = (Long) session.save(userDao);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't create User " + userDao, e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        userDao.setId(userId);
        Bucket bucket = new Bucket(userDao);
        bucket.setUserId(userId);
        bucketDao.create(bucket);
        return userDao;
    }

    @Override
    public User get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return user;
        }
    }

    @Override
    public User update(User userDao) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(userDao);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't update User " + userDao, e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userDao;
    }

    @Override
    public User delete(Long id) {
        User user = get(id);
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Can't delete user with id " + id, e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User").list();
        }
    }

    @Override
    public User login(String login, String psw)
            throws AuthenticationException {
        User user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User WHERE login=:login");
            query.setParameter("login", login);
            user = (User) query.uniqueResult();
            if (user.getPassword().equals(HashUtil.hashPassword(psw, user.getSalt()))) {
                return user;
            }
        }
        if (user == null) {
            throw new AuthenticationException("incorrect username or password");
        }
        return user;
    }

    @Override
    public Optional<User> getByToken(String token) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User WHERE token=:token");
            query.setParameter("token", token);
            List<User> list = query.list();
            return list.stream().findFirst();
        }
    }
}
