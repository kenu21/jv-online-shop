package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static long idGenerator = 0;
    private final Long id;
    private String name;
    private String login;
    private String password;
    private String token;
    private List<Order> orders;

    public User(String name, String login, String password) {
        id = idGenerator++;
        this.name = name;
        this.login = login;
        this.password = password;
        orders = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", orders=" + orders + '}';
    }
}
