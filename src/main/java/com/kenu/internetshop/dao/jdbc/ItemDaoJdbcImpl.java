package com.kenu.internetshop.dao.jdbc;

import com.kenu.internetshop.annotations.Dao;
import com.kenu.internetshop.dao.ItemDao;
import com.kenu.internetshop.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static final Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        PreparedStatement statement = null;
        String query = "INSERT INTO items (name, price) VALUES (?, ?);";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, item.getName());
            statement.setDouble(1, item.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't create item", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return item;
    }

    @Override
    public Item get(Long id) {
        PreparedStatement statement = null;
        String query = "SELECT * FROM items WHERE item_id = ?;";
        Item item = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Item> list = createListItemFromResultSet(resultSet);
            return list.get(0);
        } catch (SQLException e) {
            logger.warn("Can't get item by id=" + id);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return item;
    }

    @Override
    public Item update(Item item) {
        PreparedStatement statement = null;
        String query = "UPDATE items SET name = ? , price = ? where item_id = ?;";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setLong(3, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't update item", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return item;
    }

    @Override
    public Item delete(Long id) {
        PreparedStatement statement = null;
        Item item = get(id);
        String query = "DELETE FROM items WHERE item_id = ?;";
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can't delete item", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        PreparedStatement statement = null;
        String query = "SELECT * FROM items;";
        List<Item> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            list = createListItemFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.warn("Can't get list", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return list;
    }

    private List<Item> createListItemFromResultSet(ResultSet resultSet) {
        List<Item> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                list.add(item);
            }
            return list;
        } catch (SQLException e) {
            logger.warn("Can't createItemFromResultSet", e);
            return list;
        }
    }
}
