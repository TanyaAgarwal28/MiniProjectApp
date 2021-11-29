package com.example.miniprojectapp;

import java.io.Serializable;

public class HistoryRecord implements Serializable {
    String barcodeNumber,manufacturingDate,expiryDate,price,name;

    public HistoryRecord(String barcodeNumber, String manufacturingDate, String expiryDate, String price, String name) {
        this.barcodeNumber = barcodeNumber;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.price = price;
        this.name = name;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
