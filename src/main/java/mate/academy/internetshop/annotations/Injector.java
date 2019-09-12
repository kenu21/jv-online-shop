package mate.academy.internetshop.annotations;

import java.lang.reflect.Field;

import mate.academy.internetshop.Main;
import mate.academy.internetshop.service.impl.BucketServiceImpl;
import mate.academy.internetshop.service.impl.ItemServiceImpl;
import mate.academy.internetshop.service.impl.OrderServiceImpl;
import mate.academy.internetshop.service.impl.UserServiceImpl;

public class Injector {
    private static void inject(Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            if (field.getDeclaredAnnotation(Inject.class) != null) {
                field.setAccessible(true);
                field.set(null, AnnotatedClassMap.getImplementation(field.getType()));
            }
        }
    }

    public static void injectDependency() throws IllegalAccessException {
        inject(ItemServiceImpl.class.getDeclaredFields());
        inject(BucketServiceImpl.class.getDeclaredFields());
        inject(OrderServiceImpl.class.getDeclaredFields());
        inject(UserServiceImpl.class.getDeclaredFields());
        inject(Main.class.getDeclaredFields());
    }
}
