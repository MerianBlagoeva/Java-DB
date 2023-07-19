package com.softuni.cardealer.model.dto;

import java.math.BigDecimal;

public class SaleInfoDto {
    private CarInformationDto car;

    private String customerName;
    private Double Discount;
    private BigDecimal carPrice;
    private BigDecimal priceWithDiscount;


    public CarInformationDto getCar() {
        return car;
    }

    public void setCar(CarInformationDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        this.Discount = discount;
    }

    public BigDecimal getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(BigDecimal carPrice) {
        this.carPrice = carPrice;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
