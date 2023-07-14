package com.softuni.cardealer.model.dto;

import com.softuni.cardealer.model.entity.Car;
import com.softuni.cardealer.model.entity.Customer;

public class SaleSeedDto {
    private Integer discount;
    private Car car;
    private Customer customer;

    public SaleSeedDto() {
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
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
