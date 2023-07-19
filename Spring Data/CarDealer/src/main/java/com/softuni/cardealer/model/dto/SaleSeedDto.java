package com.softuni.cardealer.model.dto;

import com.softuni.cardealer.model.entity.Car;
import com.softuni.cardealer.model.entity.Customer;

public class SaleSeedDto {
    private Double discount;
    private Car car;
    private Customer customer;

    public SaleSeedDto() {
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
