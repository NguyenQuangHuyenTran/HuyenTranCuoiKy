package com.example.huyentran.huyentrancuoiky.model;

public class SpendingModel {
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public int getCatalogId() {
        return CatalogId;
    }

    public void setCatalogId(int catalogId) {
        CatalogId = catalogId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    private int Id;
    private int Money;
    private int CatalogId;
    private String Date;
    private String Description;
    private int UserId;
}
