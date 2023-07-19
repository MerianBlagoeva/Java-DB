package com.softuni.cardealer.model.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    private Double discount;

    private Car car;

    private Customer customer;

    public Sale() {
    }

    @Column
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
    @ManyToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(discount, sale.discount) && Objects.equals(car, sale.car) && Objects.equals(customer, sale.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discount, car, customer);
    }
}
