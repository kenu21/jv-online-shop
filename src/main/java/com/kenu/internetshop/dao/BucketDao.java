package com.kenu.internetshop.dao;

import com.kenu.internetshop.model.Bucket;
import com.kenu.internetshop.model.Item;

import java.util.List;

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
