package com.kenu.internetshop.service.impl;

import com.kenu.internetshop.annotations.Inject;
import com.kenu.internetshop.annotations.Service;
import com.kenu.internetshop.dao.ItemDao;
import com.kenu.internetshop.model.Item;
import com.kenu.internetshop.service.ItemService;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Inject
    private static ItemDao itemDao;

    @Override
    public Item create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) {
        return itemDao.get(id);
    }

    @Override
    public Item update(Item item) {
        return itemDao.update(item);
    }

    @Override
    public Item delete(Long id) {
        return itemDao.delete(id);
    }

    @Override
    public List<Item> getAllItems() {
        return itemDao.getAllItems();
    }
}
