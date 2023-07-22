package com.softuni.nextleveltechnologies.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
