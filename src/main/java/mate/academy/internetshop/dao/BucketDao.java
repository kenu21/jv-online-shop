package mate.academy.internetshop.dao;

import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

public interface BucketDao {

    Bucket create(Bucket bucket);

    Bucket get(Long bucketId);

    Bucket update(Bucket bucket);

    Bucket delete(Long id);

    Bucket getByUserId(Long userId);

    Boolean addItem(Long bucketId, Long itemId);

    List<Item> getItems(Long bucketId);

    Boolean deleteItem(Long bucketId, Long itemId);

    Boolean clear(Long bucketId);
}
