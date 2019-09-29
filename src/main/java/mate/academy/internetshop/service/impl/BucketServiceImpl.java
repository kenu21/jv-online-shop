package mate.academy.internetshop.service.impl;

import java.util.List;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.annotations.Service;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {

    @Inject
    private static BucketDao bucketDao;

    @Inject
    private static ItemDao itemDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id);
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public void delete(Long id) {
        bucketDao.delete(id);
    }

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        Bucket bucket = bucketDao.get(bucketId);
        Item item = itemDao.get(itemId);
        bucket.getItems().add(item);
        bucketDao.addItem(bucketId, itemId);
        return bucket;
    }

    @Override
    public Bucket deleteItem(Long bucketId, Long itemId) {
        Bucket bucket = bucketDao.get(bucketId);
        List<Item> list = bucket.getItems();
        Item item = null;
        for (Item it : list) {
            if (itemId.equals(it.getId())) {
                item = it;
                break;
            }
        }
        bucket.getItems().remove(item);
        bucketDao.deleteItem(bucketId, itemId);
        return bucket;
    }

    @Override
    public Bucket clear(Long bucketId) {
        Bucket bucket = bucketDao.get(bucketId);
        bucket.getItems().clear();
        update(bucket);
        bucketDao.clear(bucketId);
        return bucket;
    }

    @Override
    public List getAllItems(Long bucketId) {
        Bucket bucket = bucketDao.get(bucketId);
        List<Item> list = bucketDao.getItems(bucketId);
        bucket.setItems(list);
        return bucket.getItems();
    }

    @Override
    public Bucket getByUserId(Long userId) {
        return bucketDao.getByUserId(userId);
    }
}
