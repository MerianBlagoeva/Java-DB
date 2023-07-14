package com.softuni.cardealer.model.dto;

import com.softuni.cardealer.model.entity.Sale;

import java.time.LocalDate;
import java.util.Set;

public class CustomerInfoDto {
    private Long id;
    private String name;
    private String BirthDate;
    private Boolean isYoungDriver;
    private Set<Sale> sales;

    public CustomerInfoDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
