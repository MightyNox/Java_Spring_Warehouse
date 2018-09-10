package com.main.warehouse.dao;

import com.main.warehouse.entity.Country;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryDao extends CrudRepository<Country, Long> {
    List<Country> findAll();
    Country findByCountryCode(String code);
    Country findAllByCountryName(String name);
}
