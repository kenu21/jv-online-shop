package com.kenu.internetshop.service;

import com.kenu.internetshop.model.Bucket;
import com.kenu.internetshop.model.Item;

import java.util.List;

public interface BucketService {
    Bucket create(Bucket bucket);

    Bucket get(Long id);

    Bucket update(Bucket bucket);

    void delete(Long id);

    Bucket addItem(Long bucketId, Long itemId);

    Bucket deleteItem(Long bucketId, Long itemId);

    Bucket clear(Long bucketId);

    List<Item> getAllItems(Long bucketId);

    Bucket getByUserId(Long userId);
}
