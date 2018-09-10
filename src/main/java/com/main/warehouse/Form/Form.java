package com.main.warehouse.Form;

import com.main.warehouse.entity.Category;
import com.main.warehouse.entity.Country;

public class Form {
    private Category category;
    private Country country;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
