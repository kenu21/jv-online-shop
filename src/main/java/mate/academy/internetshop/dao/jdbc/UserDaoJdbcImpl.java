package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        String queryUsers = String.format("INSERT INTO users (name, login, password, token) "
                        + "VALUES ('%s', '%s', '%s', '%s');", userDao.getName(), userDao.getLogin(),
                userDao.getPassword(), userDao.getToken());
        try (Statement statementUsers = connection.createStatement()) {
            statementUsers.executeUpdate(queryUsers, Statement.RETURN_GENERATED_KEYS);
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
        String queryUsers = String.format("SELECT * FROM users WHERE user_id = %d;", id);
        String queryRoles = String.format("SELECT role_name FROM roles r INNER JOIN users_roles ur "
                + "ON r.role_id = ur.role_id where ur.user_id = %d;", id);
        User user = null;
        try (Statement statementUsers = connection.createStatement();
                Statement statementRoles = connection.createStatement();
                ResultSet resultSetUsers = statementUsers.executeQuery(queryUsers);
                ResultSet resultSetRoles = statementRoles.executeQuery(queryRoles)) {
            while (resultSetUsers.next()) {
                Long userId = resultSetUsers.getLong("user_id");
                String name = resultSetUsers.getString("name");
                String login = resultSetUsers.getString("login");
                String password = resultSetUsers.getString("password");
                String token = resultSetUsers.getString("token");
                user = new User(name, login, password);
                user.setId(userId);
                user.setToken(token);
            }
            while (resultSetRoles.next()) {
                Role role = Role.of(resultSetRoles.getString("role_name"));
                user.getRoles().add(role);
            }
        } catch (SQLException e) {
            logger.error("Can't get user", e);
        }
        return user;
    }

    @Override
    public User update(User userDao) {
        String queryUsers = String.format(
                "UPDATE users SET name = '%s', login = '%s', password = '%s' "
                        + "WHERE user_id = %d;",
                userDao.getName(), userDao.getLogin(), userDao.getPassword(), userDao.getId());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(queryUsers);
        } catch (SQLException e) {
            logger.error("Can't update user", e);
        }
        return userDao;
    }

    @Override
    public User delete(Long id) {
        User user = get(id);
        String queryUsers = String.format("DELETE FROM users WHERE user_id = %d;", id);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(queryUsers);
        } catch (SQLException e) {
            logger.error("Can't update user", e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String queryUsers = "SELECT * FROM users";

        User user = null;

        try (Statement statementUsers = connection.createStatement();
                ResultSet resultSetUsers = statementUsers.executeQuery((queryUsers))) {
            while (resultSetUsers.next()) {
                Long userId = resultSetUsers.getLong("user_id");
                String name = resultSetUsers.getString("name");
                String login = resultSetUsers.getString("login");
                String password = resultSetUsers.getString("password");
                String token = resultSetUsers.getString("token");
                user = new User(name, login, password);
                user.setId(userId);
                user.setToken(token);

                String queryRoles = String.format(
                        "SELECT role_name FROM roles r INNER JOIN users_roles ur "
                        + "ON r.role_id = ur.role_id where ur.user_id = %d;", userId);
                try (Statement statementRoles = connection.createStatement();
                        ResultSet resultSetRoles = statementRoles.executeQuery(queryRoles)) {
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
        List<User> list = getAll();
        Optional<User> user = list.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
        if (user.isEmpty() || !user.get().getPassword().equals(psw)) {
            throw new AuthenticationException("incorrect username or password");
        }
        return user.get();
    }

    @Override
    public Optional<User> getByToken(String token) {
        List<User> list = getAll();
        return list.stream()
                .filter(u -> u.getToken().equals(token))
                .findFirst();
    }
}
