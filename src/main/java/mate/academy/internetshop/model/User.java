package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static long idGenerator = 0;
    private final Long id;
    private String name;
    private List<Order> orders;

    public User(String name) {
        id = idGenerator++;
        this.name = name;
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", orders=" + orders + '}';
    }
}
