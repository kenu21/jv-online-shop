package com.kenu.internetshop.service.impl;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.annotations.Service;
import com.kenu.internetshop.dao.BucketDao;
import com.kenu.internetshop.dao.ItemDao;
import com.kenu.internetshop.model.Bucket;
import com.kenu.internetshop.model.Item;
import com.kenu.internetshop.service.BucketService;

import java.util.List;

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
