package mate.academy.internetshop.service.impl;

import java.util.List;

import mate.academy.internetshop.annotations.Inject;
import mate.academy.internetshop.annotations.Service;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;

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
    public void delete(Long id) {
        itemDao.delete(id);
    }

    @Override
    public void delete(Item item) {
        itemDao.delete(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemDao.getAllItems();
    }
}
