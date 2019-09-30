package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.annotations.Dao;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;

@Dao
public class BucketDaoImpl implements BucketDao {

    @Override
    public Bucket create(Bucket bucket) {
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        return Storage.buckets
                .stream().filter(b -> b.getId().equals(bucketId))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find bucket with id " + bucketId));
    }

    @Override
    public Bucket update(Bucket bucket) {
        for (int i = 0; i < Storage.buckets.size(); i++) {
            if (Storage.buckets.get(i).getId().equals(bucket.getId())) {
                Storage.buckets.set(i, bucket);
                return bucket;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public Bucket delete(Long id) {
        Bucket bucket = get(id);
        Storage.buckets.removeIf((b) -> b.getId().equals(id));
        return bucket;
    }

    @Override
    public Bucket getByUserId(Long userId) {
        return null;
    }

    @Override
    public Boolean addItem(Long bucketId, Long itemId) {
        return null;
    }

    @Override
    public List<Item> getItems(Long bucketId) {
        return null;
    }

    @Override
    public Boolean deleteItem(Long bucketId, Long itemId) {
        // don't use
        return null;
    }

    @Override
    public Boolean clear(Long bucketId) {
        // don't use
        return null;
    }
}
