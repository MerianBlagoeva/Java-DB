package com.softuni.cardealer.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private LocalDate birthDate;
    private Boolean isYoungDriver;
    private Set<Sale> sales;

    public Customer() {
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "birth_date")
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "is_young_driver")
    public Boolean getIsYoungDriver() {
        return isYoungDriver;
    }

    public void setIsYoungDriver(Boolean is_young_driver) {
        this.isYoungDriver = is_young_driver;
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(birthDate, customer.birthDate) && Objects.equals(isYoungDriver, customer.isYoungDriver) && Objects.equals(sales, customer.sales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthDate, isYoungDriver, sales);
    }
}
