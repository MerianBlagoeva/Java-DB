package com.softuni.cardealer.model.dto;

import java.math.BigDecimal;

public class PartNameAndPriceDto {
    private String Name;
    private BigDecimal Price;

    public PartNameAndPriceDto() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }
}
