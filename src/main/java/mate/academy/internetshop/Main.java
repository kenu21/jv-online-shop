package mate.academy.internetshop;

import java.util.List;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.annotations.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {
    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private static BucketService bucketService;

    @Inject
    private static ItemService itemService;

    @Inject
    private static UserService userService;

    @Inject
    private static OrderService orderService;

    public static void main(String[] args) {
        Item iphone = new Item("iphone 11", 1000D);
        itemService.create(iphone);

        User vasya = new User("Vasya");
        userService.create(vasya);

        Bucket bucket = new Bucket(vasya.getId());
        bucketService.create(bucket);
        bucketService.addItem(bucket.getId(), iphone.getId());

        orderService.completeOrder(bucket.getItems(), vasya.getId());
        bucketService.clear(bucket.getId());

        List<Order> allOrdersForUser = orderService.getAllOrdersForUser(vasya.getId());
        allOrdersForUser.forEach(System.out::println);
    }
}
