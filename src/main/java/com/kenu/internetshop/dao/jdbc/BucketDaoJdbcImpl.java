package com.kenu.internetshop.dao.jdbc;

import com.kenu.internetshop.annotations.Dao;
import com.kenu.internetshop.dao.BucketDao;
import com.kenu.internetshop.model.Bucket;
import com.kenu.internetshop.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static final Logger logger = Logger.getLogger(BucketDaoJdbcImpl.class);

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) {
        String queryBuckets = "INSERT INTO buckets (user_id) VALUES (?);";
        try (PreparedStatement statementBuckets = connection.prepareStatement(
                queryBuckets, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statementBuckets.setLong(1, bucket.getUserId());
            statementBuckets.executeUpdate();
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
        String queryBuckets = "SELECT * FROM buckets WHERE bucket_id = ?;";
        try (PreparedStatement statementBuckets = connection.prepareStatement(queryBuckets)) {
            statementBuckets.setLong(1, bucketId);
            ResultSet resultSet = statementBuckets.executeQuery();
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
        String queryBucktes = "SELECT * FROM buckets WHERE user_id = ?;";
        try (PreparedStatement statementBuckets = connection.prepareStatement(queryBucktes)) {
            statementBuckets.setLong(1, userId);
            ResultSet resultSet = statementBuckets.executeQuery();
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
        String queryBucktes =
                "INSERT INTO buckets_items (bucket_id, item_id) VALUES (?, ?);";
        try (PreparedStatement statementBuckets = connection.prepareStatement(queryBucktes)) {
            statementBuckets.setLong(1, bucketId);
            statementBuckets.setLong(2, itemId);
            statementBuckets.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add item", e);
            return false;
        }
        return true;
    }

    @Override
    public List<Item> getItems(Long bucketId) {
        List<Item> list = new ArrayList<>();
        String queryBucktes = "SELECT * FROM items i INNER JOIN buckets_items bi "
                + "ON i.item_id = bi.item_id WHERE bucket_id = ?;";
        try (PreparedStatement statementBuckets = connection.prepareStatement(queryBucktes)) {
            statementBuckets.setLong(1, bucketId);
            ResultSet resultSet = statementBuckets.executeQuery();
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
        String queryBucktes =
                "DELETE FROM buckets_items WHERE bucket_id = ? AND item_id = ?;";
        try (PreparedStatement statementBuckets = connection.prepareStatement(queryBucktes)) {
            statementBuckets.setLong(1, bucketId);
            statementBuckets.setLong(2, itemId);
            statementBuckets.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete item", e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean clear(Long bucketId) {
        String queryBucktes = "DELETE FROM buckets_items WHERE bucket_id = ?;";
        try (PreparedStatement statementBuckets = connection.prepareStatement(queryBucktes)) {
            statementBuckets.setLong(1, bucketId);
            statementBuckets.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't clear bucket", e);
            return false;
        }
        return true;
    }

    @Override
    public Bucket update(Bucket bucket) {
        String queryBucktes =
                "UPDATE buckets SET user_id = ? WHERE bucket_id = ?;";
        try (PreparedStatement statementBuckets = connection.prepareStatement(queryBucktes)) {
            statementBuckets.setLong(1, bucket.getUserId());
            statementBuckets.setLong(2, bucket.getId());
            statementBuckets.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't update bucket", e);
        }
        return bucket;
    }

    @Override
    public Bucket delete(Long id) {
        Bucket bucket = get(id);
        String queryBucktes = "DELETE FROM buckets WHERE bucket_id = ?;";
        try (PreparedStatement statementBuckets = connection.prepareStatement(queryBucktes)) {
            statementBuckets.setLong(1, id);
            statementBuckets.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete bucket", e);
        }
        return bucket;
    }
}
