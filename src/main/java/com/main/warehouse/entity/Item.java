package com.main.warehouse.entity;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemId;

    @NotNull
    private String itemName;

    @NotNull
    private long itemQuantity;

    @NotNull
    private double itemPrice;


    @ManyToOne
    private Category itemCategory;

    @ManyToOne
    private Country itemCountry;

    private String itemDescription;

    public Item(@NotNull String itemName, @NotNull long itemQuantity, @NotNull double itemPrice, Category itemCategory, Country itemCountry, String itemDescription) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemCountry = itemCountry;
        this.itemDescription = itemDescription;
    }

    public long getItemID() {
        return itemId;
    }

    public void setItemID(long itemID) {
        this.itemId = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
