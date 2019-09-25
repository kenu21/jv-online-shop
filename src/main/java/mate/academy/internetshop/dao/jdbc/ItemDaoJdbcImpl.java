package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static final Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static final String DB_NAME = "dbinternetshop";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        Statement stmt = null;
        String query = "Insert into " + DB_NAME + ".items (name, price) Values ("
                + "'" + item.getName() + "'" + ", " + item.getPrice() + ");";
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can't create item", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item get(Long id) {
        Statement stmt = null;
        String query = "Select * from " + DB_NAME + ".items where item_id = " + id + ";";

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                return item;
            }
        } catch (SQLException e) {
            logger.warn("Can't get item by id=" + id);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item update(Item item) {
        Statement stmt = null;
        String query = "Update " + DB_NAME + ".items Set name =" + "'" + item.getName()
                + "'" + ", price=" + item.getPrice() + "where item_id = " + item.getId() + ";";
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can't update item", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item delete(Long id) {
        Statement stmt = null;
        Item item = get(id);
        String query = "Delete from " + DB_NAME + ".items where item_id = " + id + ";";
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can't delete item", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        Statement stmt = null;
        String query = "Select * from " + DB_NAME + ".items;";
        List<Item> list = new ArrayList<>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                list.add(item);
            }
            return list;
        } catch (SQLException e) {
            logger.warn("Can't get list", e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    logger.warn("Can't close statement ", e);
                }
            }
        }
        return null;
    }
}
