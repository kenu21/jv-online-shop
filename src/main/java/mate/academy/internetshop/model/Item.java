package mate.academy.internetshop.model;

public class Item {
    private static long idGeneraor = 0;
    private final Long id;
    private String name;
    private Double price;

    public Item(String name, Double price) {
        id = idGeneraor++;
        this.name = name;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}
