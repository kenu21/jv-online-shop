package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private static long idGenerator = 0;

    private final Long id;
    private final Long userId;
    private List<Item> items;

    public Bucket(Long userId) {
        this.userId = userId;
        id = idGenerator++;
        items = new ArrayList<>();
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

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
