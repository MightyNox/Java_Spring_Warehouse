package com.main.warehouse.Form;

import com.main.warehouse.entity.Category;
import com.main.warehouse.entity.Country;

public class DisplayGoodsForm {
    private String name;
    private Long quantity;
    private Double price;
    private String category;
    private String country;
    private String description;

    public DisplayGoodsForm() {
    }

    public DisplayGoodsForm(String name, Long quantity, Double price, Category category, Country country, String description) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        if(category != null)
            this.category = category.getCategoryName();
        else
            this.category="--";
        if(country != null)
            this.country = country.getCountryName();
        else
            this.country = "--";
        if (!description.isEmpty())
            this.description = description;
        else
            this.description = "--";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
