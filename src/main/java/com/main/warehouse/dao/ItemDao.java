package com.main.warehouse.dao;

import com.main.warehouse.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemDao extends CrudRepository<Item, Long> {
    List<Item> findAll();
    Item findByItemName(String name);
}