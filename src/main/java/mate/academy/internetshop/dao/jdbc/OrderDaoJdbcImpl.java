package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String queryOrders = "INSERT INTO orders (user_id) VALUES (?);";
        try (PreparedStatement statementOrder = connection.prepareStatement(
                queryOrders, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statementOrder.setLong(1, order.getUserId());
            statementOrder.executeUpdate();
            ResultSet generatedKeys = statementOrder.getGeneratedKeys();
            generatedKeys.next();
            Long orderId = generatedKeys.getLong(1);
            order.setId(orderId);
            List<Item> list = order.getItems();
            for (Item item : list) {
                String queryItem = "INSERT INTO orders_items (order_id, item_id) "
                        + "VALUES (?, ?);";
                try (PreparedStatement statementItem = connection.prepareStatement(queryItem)) {
                    statementItem.setLong(1, orderId);
                    statementItem.setLong(2, item.getId());
                    statementItem.executeUpdate();
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
        String query = "SELECT o.order_id, o.user_id, i.item_id, i.name, i.price "
                + "FROM orders o INNER JOIN orders_items oi ON o.order_id = oi.order_id "
                + "INNER JOIN items i ON oi.item_id = i.item_id WHERE o.order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
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
        String query = "UPDATE orders SET user_id = ? WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't update order", e);
        }
        return order;
    }

    @Override
    public Order delete(Long id) {
        Order order = get(id);
        String query = "DELETE FROM orders WHERE order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete order", e);
        }
        return order;
    }

    @Override
    public List<Order> getOrders(Long userId) {
        List<Order> list = new ArrayList<>();
        String queryOrders = "SELECT * FROM orders WHERE user_id = ?;";
        try (PreparedStatement statementOrder = connection.prepareStatement(queryOrders)) {
            statementOrder.setLong(1, userId);
            ResultSet resultSet = statementOrder.executeQuery();
            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                Order order = new Order(userId);
                order.setId(orderId);
                List<Item> listItem = getAllItemsFromOrder(orderId, userId);
                order.setItems(listItem);
                list.add(order);
            }
        } catch (SQLException e) {
            logger.error("Can't get orders", e);
        }
        return list;
    }

    private List<Item> getAllItemsFromOrder(Long orderId, Long userId) {
        List<Item> listItem = new ArrayList<>();
        String queryItems =
                "SELECT * FROM items i INNER JOIN orders_items oi "
                        + "ON i.item_id = oi.item_id "
                        + "INNER JOIN orders o ON oi.order_id = o.order_id "
                        + "WHERE o.user_id = ? and o.order_id = ?;";
        try (PreparedStatement statementItems = connection.prepareStatement(queryItems)) {
            statementItems.setLong(1, userId);
            statementItems.setLong(2, orderId);
            ResultSet resultSetItems = statementItems.executeQuery();
            while (resultSetItems.next()) {
                Long itemId = resultSetItems.getLong("item_id");
                String name = resultSetItems.getString("name");
                Double price = resultSetItems.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                listItem.add(item);
            }
        } catch (SQLException e) {
            logger.error("Can't get list items by order and user", e);
        }
        return listItem;
    }
}
