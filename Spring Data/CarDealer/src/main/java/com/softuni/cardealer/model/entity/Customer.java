package com.softuni.cardealer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private LocalDate birthDate;
    private Boolean isYoungDriver;

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
}
