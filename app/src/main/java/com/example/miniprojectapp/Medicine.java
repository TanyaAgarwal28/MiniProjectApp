package com.example.miniprojectapp;

import java.util.HashMap;
import java.util.Map;

public class Medicine {
    String barcodeNumber,price,expiryDate,manufacturingDate,name;

    public Medicine(){}

    public Medicine(String barcodeNumber, String price, String expiryDate, String manufacturingDate, String name) {
        this.barcodeNumber = barcodeNumber;
        this.price = price;
        this.expiryDate = expiryDate;
        this.manufacturingDate = manufacturingDate;
        this.name = name;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("barcodeNumber", barcodeNumber);
        result.put("price", price);
        result.put("manufacturingDate", manufacturingDate);
        result.put("expiryDate", expiryDate);
        result.put("name",name);
        return result;
    }
}
