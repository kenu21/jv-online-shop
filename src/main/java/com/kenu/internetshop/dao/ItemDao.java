package com.kenu.internetshop.dao;

import com.kenu.internetshop.model.Item;

import java.util.List;

public interface ItemDao {

    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    Item delete(Long id);

    List<Item> getAllItems();
}
