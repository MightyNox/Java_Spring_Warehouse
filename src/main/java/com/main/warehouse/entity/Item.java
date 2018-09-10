package com.main.warehouse.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;

    @NotNull
    private String itemName;

    @NotNull
    private Long itemQuantity;

    @NotNull
    private Double itemPrice;

    @ManyToOne
    private Category itemCategory;

    @ManyToOne
    private Country itemCountry;

    private String itemDescription;

    public Item(@NotNull String itemName, @NotNull Long itemQuantity, @NotNull Double itemPrice, Category itemCategory, Country itemCountry, String itemDescription) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemCountry = itemCountry;
        this.itemDescription = itemDescription;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Category getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(Category itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Country getItemCountry() {
        return itemCountry;
    }

    public void setItemCountry(Country itemCountry) {
        this.itemCountry = itemCountry;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
