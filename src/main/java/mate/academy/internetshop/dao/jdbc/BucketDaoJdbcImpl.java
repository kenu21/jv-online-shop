package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

import org.apache.log4j.Logger;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static final Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) {
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format("INSERT INTO buckets (user_id) VALUES (%d);",
                    bucket.getUserId());
            statementBuckets.executeUpdate(queryBucktes, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statementBuckets.getGeneratedKeys();
            generatedKeys.next();
            Long bucketId = generatedKeys.getLong(1);
            bucket.setId(bucketId);
        } catch (SQLException e) {
            logger.error("Can't create bucket", e);
        }
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        Bucket bucket = null;
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format("SELECT * from buckets where bucket_id = %d;",
                    bucketId);
            ResultSet resultSet = statementBuckets.executeQuery(queryBucktes);
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                bucket = new Bucket(userId);
                bucket.setId(bucketId);
            }
        } catch (SQLException e) {
            logger.error("Can't get bucket", e);
        }
        return bucket;
    }

    @Override
    public Bucket getByUserId(Long userId) {
        Bucket bucket = null;
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format("SELECT * from buckets where user_id = %d;",
                    userId);
            ResultSet resultSet = statementBuckets.executeQuery(queryBucktes);
            while (resultSet.next()) {
                Long bucketId = resultSet.getLong("bucket_id");
                bucket = new Bucket(userId);
                bucket.setId(bucketId);
            }
        } catch (SQLException e) {
            logger.error("Can't get bucket", e);
        }
        return bucket;
    }

    @Override
    public Boolean addItem(Long bucketId, Long itemId) {
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format(
                    "INSERT INTO buckets_items (bucket_id, item_id) VALUES (%d, %d);",
                    bucketId, itemId);
            statementBuckets.executeUpdate(queryBucktes);
        } catch (SQLException e) {
            logger.error("Can't add item", e);
        }
        return true;
    }

    @Override
    public List<Item> getItems(Long bucketId) {
        List<Item> list = new ArrayList<>();
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format("SELECT * FROM items i INNER JOIN buckets_items bi "
                    + "ON i.item_id = bi.item_id WHERE bucket_id = %d;", bucketId);
            ResultSet resultSet = statementBuckets.executeQuery(queryBucktes);
            while (resultSet.next()) {
                Long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                list.add(item);
            }
        } catch (SQLException e) {
            logger.error("Can't add item", e);
        }
        return list;
    }

    @Override
    public Boolean deleteItem(Long bucketId, Long itemId) {
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format(
                    "DELETE FROM buckets_items where bucket_id = %d and item_id = %d;",
                    bucketId, itemId);
            statementBuckets.executeUpdate(queryBucktes);
        } catch (SQLException e) {
            logger.error("Can't delete item", e);
        }
        return true;
    }

    @Override
    public Boolean clear(Long bucketId) {
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format("DELETE FROM buckets_items WHERE bucket_id = %d;",
                    bucketId);
            statementBuckets.executeUpdate(queryBucktes);
        } catch (SQLException e) {
            logger.error("Can't clear bucket", e);
        }
        return true;
    }

    @Override
    public Bucket update(Bucket bucket) {
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format(
                    "UPDATE buckets SET user_id = %d WHERE bucket_id = %d;",
                    bucket.getUserId(), bucket.getId());
            statementBuckets.executeUpdate(queryBucktes);
        } catch (SQLException e) {
            logger.error("Can't update bucket", e);
        }
        return bucket;
    }

    @Override
    public Bucket delete(Long id) {
        Bucket bucket = get(id);
        try (Statement statementBuckets = connection.createStatement()) {
            String queryBucktes = String.format("DELETE FROM buckets WHERE bucket_id = %d;",
                    id);
            statementBuckets.executeUpdate(queryBucktes);
        } catch (SQLException e) {
            logger.error("Can't delete bucket", e);
        }
        return bucket;
    }
}
