package com.softuni.cardealer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity {
    private String name;
    private Boolean isImporter;

    public Supplier() {
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_importer")
    public Boolean getImporter() {
        return isImporter;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name) && Objects.equals(isImporter, supplier.isImporter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isImporter);
    }
}
