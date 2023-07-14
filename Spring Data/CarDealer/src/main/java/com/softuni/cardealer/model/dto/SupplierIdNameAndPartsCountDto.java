package com.softuni.cardealer.model.dto;

public class SupplierIdNameAndPartsCountDto {
    private Long Id;
    private String Name;
    private Integer partsCount;

    public SupplierIdNameAndPartsCountDto() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }
}
