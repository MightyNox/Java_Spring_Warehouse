package com.main.warehouse.dao;

import com.main.warehouse.entity.Category;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CategoryDao extends CrudRepository<Category, Long> {
    List<Category> findAll();
    Category findByCategoryName(@NotNull String name);
}
