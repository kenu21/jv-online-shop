package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private static long idGenerator = 0;
    private final Long id;
    private String name;
    private String login;
    private String password;
    private String token;
    private Set<Role> roles;
    private List<Order> orders;

    public User(String name, String login, String password) {
        id = idGenerator++;
        this.name = name;
        this.login = login;
        this.password = password;
        roles = new HashSet<>();
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", orders=" + orders + '}';
    }
}
