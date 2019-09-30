package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    private static final Logger logger = Logger.getLogger(OrderDaoJdbcImpl.class);

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) {
        try (Statement statementOrder = connection.createStatement()) {
            String queryOrders = String.format("INSERT INTO orders (user_id) VALUES (%d);",
                    order.getUserId());
            statementOrder.executeUpdate(queryOrders, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statementOrder.getGeneratedKeys();
            generatedKeys.next();
            Long orderId = generatedKeys.getLong(1);
            order.setId(orderId);
            List<Item> list = order.getItems();
            for (Item item : list) {
                try (Statement statementItem = connection.createStatement()) {
                    String queryItem = String.format("INSERT INTO orders_items (order_id, item_id) "
                            + "VALUES (%d, %d);", orderId, item.getId());
                    statementItem.executeUpdate(queryItem);
                }
            }
        } catch (SQLException e) {
            logger.error("Can't create order", e);
        }
        return order;
    }

    @Override
    public Order get(Long id) {
        Order order = null;
        try (Statement statement = connection.createStatement()) {
            String query = String.format("SELECT o.order_id, o.user_id, i.item_id, i.name, i.price "
                    + "FROM orders o INNER JOIN orders_items oi ON o.order_id = oi.order_id "
                    + "INNER JOIN items i ON oi.item_id = i.item_id WHERE o.order_id = %d;", id);
            ResultSet resultSet = statement.executeQuery(query);
            List<Item> list = new ArrayList<>();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                Long userId = resultSet.getLong("user_id");
                order = new Order(userId);
                order.setId(orderId);
                Long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                list.add(item);
            }
            order.setItems(list);
        } catch (SQLException e) {
            logger.error("Can't get order", e);
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        try (Statement statement = connection.createStatement()) {
            String query = String.format("UPDATE orders SET user_id = %d WHERE order_id = %d;",
                    order.getUserId(), order.getId());
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Can't update order", e);
        }
        return order;
    }

    @Override
    public Order delete(Long id) {
        Order order = get(id);
        try (Statement statement = connection.createStatement()) {
            String query = String.format("DELETE FROM orders WHERE order_id = %d;",
                    id);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Can't delete order", e);
        }
        return order;
    }

    @Override
    public List<Order> getOrders(Long userId) {
        List<Order> list = new ArrayList<>();
        try (Statement statementOrder = connection.createStatement()) {
            String queryOrders = String.format("SELECT * FROM orders WHERE user_id = %d;",
                    userId);
            ResultSet resultSet = statementOrder.executeQuery(queryOrders);
            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                Order order = new Order(userId);
                order.setId(orderId);
                List<Item> listItem = new ArrayList<>();
                try (Statement statementItems = connection.createStatement()) {
                    String queryItems = String.format(
                            "SELECT * FROM items i INNER JOIN orders_items oi "
                            + "ON i.item_id = oi.item_id "
                                    + "INNER JOIN orders o ON oi.order_id = o.order_id "
                            + "WHERE o.user_id = %d and o.order_id = %d;", userId, orderId);
                    ResultSet resultSetItems = statementItems.executeQuery(queryItems);
                    while (resultSetItems.next()) {
                        Long itemId = resultSetItems.getLong("item_id");
                        String name = resultSetItems.getString("name");
                        Double price = resultSetItems.getDouble("price");
                        Item item = new Item(name, price);
                        item.setId(itemId);
                        listItem.add(item);
                    }
                }
                order.setItems(listItem);
                list.add(order);
            }

        } catch (SQLException e) {
            logger.error("Can't create order", e);
        }
        return list;
    }
}
