package mate.academy.internetshop.model;

import java.util.List;

public class Order {
    private static long idGenerator = 0;
    private final Long id;
    private final Long userId;
    private final List<Item> items;

    public Order(Long userId, List<Item> items) {
        this.userId = userId;
        this.items = items;
        id = idGenerator++;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId=" + userId + ", items=" + items + '}';
    }
}
