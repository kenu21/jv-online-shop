package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    @Inject
    private static BucketDao bucketDao;

    private static final Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User userDao) {
        String queryUsers = "INSERT INTO users (name, login, password, token) "
                        + "VALUES (?, ?, ?, ?);";
        try (PreparedStatement statementUsers = connection.prepareStatement(
                queryUsers, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statementUsers.setString(1, userDao.getName());
            statementUsers.setString(2, userDao.getLogin());
            statementUsers.setString(3, userDao.getPassword());
            statementUsers.setString(4, userDao.getToken());
            statementUsers.executeUpdate();
            ResultSet generatedKeys = statementUsers.getGeneratedKeys();
            generatedKeys.next();
            Long userId = generatedKeys.getLong(1);
            userDao.setId(userId);
            Bucket bucket = new Bucket(userId);
            bucketDao.create(bucket);
        } catch (SQLException e) {
            logger.error("Can't create user", e);
        }
        return userDao;
    }

    @Override
    public User get(Long id) {
        String query = "SELECT u.user_id, u.name, u.login, u.password, "
                + "u.token, r.role_name FROM users u INNER JOIN users_roles ur "
                + "ON u.user_id = ur.user_id INNER JOIN roles r "
                + "ON ur.role_id = r.role_id WHERE u.user_id = ?;";
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> set = new HashSet<>();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String token = resultSet.getString("token");
                user = new User(name, login, password);
                user.setId(userId);
                user.setToken(token);
                Role role = Role.of(resultSet.getString("role_name"));
                set.add(role);
            }
            user.setRoles(set);
        } catch (SQLException e) {
            logger.error("Can't get user", e);
        }
        return user;
    }

    @Override
    public User update(User userDao) {
        String queryUsers =
                "UPDATE users SET name = ?, login = ?, password = ? "
                        + "WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(queryUsers)) {
            statement.setString(1, userDao.getName());
            statement.setString(2, userDao.getLogin());
            statement.setString(3, userDao.getPassword());
            statement.setLong(4, userDao.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't update user", e);
        }
        return userDao;
    }

    @Override
    public User delete(Long id) {
        User user = get(id);
        String queryUsers = "DELETE FROM users WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(queryUsers)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't update user", e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String queryUsers = "SELECT * FROM users";
        try (PreparedStatement statementUsers = connection.prepareStatement(queryUsers);
                ResultSet resultSetUsers = statementUsers.executeQuery()) {
            while (resultSetUsers.next()) {
                Long userId = resultSetUsers.getLong("user_id");
                String name = resultSetUsers.getString("name");
                String login = resultSetUsers.getString("login");
                String password = resultSetUsers.getString("password");
                String token = resultSetUsers.getString("token");
                User user = new User(name, login, password);
                user.setId(userId);
                user.setToken(token);

                String queryRoles =
                        "SELECT role_name FROM roles r INNER JOIN users_roles ur "
                                + "ON r.role_id = ur.role_id where ur.user_id = ?;";
                try (PreparedStatement statementRoles = connection.prepareStatement(queryRoles)) {
                    statementRoles.setLong(1,  userId);
                    ResultSet resultSetRoles = statementRoles.executeQuery();
                    while (resultSetRoles.next()) {
                        Role role = Role.of(resultSetRoles.getString("role_name"));
                        user.getRoles().add(role);
                    }
                }
                list.add(user);
            }
        } catch (SQLException e) {
            logger.error("Can't get users", e);
        }
        return list;
    }

    @Override
    public User login(String login, String psw) throws AuthenticationException {
        User user = null;
        String query = "SELECT u.user_id, u.name, u.token, r.role_name "
                + "FROM users u INNER JOIN users_roles ur ON u.user_id = ur.user_id "
                + "INNER JOIN roles r ON ur.role_id = r.role_id "
                + "WHERE login = ? AND password = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, psw);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> set = new HashSet<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String token = resultSet.getString("token");
                user = new User(name, login, psw);
                user.setId(id);
                user.setToken(token);
                Role role = Role.of(resultSet.getString("role_name"));
                set.add(role);
            }
            if (user == null) {
                throw new AuthenticationException("incorrect username or password");
            }
            user.setRoles(set);
        } catch (SQLException e) {
            logger.error("Can't get users by login and password ", e);
        }
        return user;
    }

    @Override
    public Optional<User> getByToken(String token) {
        Optional<User> optionalUser = Optional.empty();
        String query =
                "SELECT u.user_id, u.name, u.login, u.password, r.role_name "
                        + "FROM users u INNER JOIN users_roles ur ON u.user_id = ur.user_id "
                        + "INNER JOIN roles r ON ur.role_id = r.role_id "
                        + "WHERE token = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> set = new HashSet<>();
            User user = null;
            while (resultSet.next()) {
                Long id = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String psw = resultSet.getString("password");
                user = new User(name, login, psw);
                user.setId(id);
                user.setToken(token);
                Role role = Role.of(resultSet.getString("role_name"));
                set.add(role);
            }
            user.setRoles(set);
            optionalUser = Optional.of(user);
        } catch (SQLException e) {
            logger.error("Can't get users by token ", e);
        }
        return optionalUser;
    }
}
