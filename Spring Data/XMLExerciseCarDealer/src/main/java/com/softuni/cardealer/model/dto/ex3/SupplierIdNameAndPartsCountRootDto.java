package com.softuni.cardealer.model.dto.ex3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierIdNameAndPartsCountRootDto {

    @XmlElement(name = "supplier")
    private List<SupplierIdNameAndPartsCountDto> suppliers;

    public List<SupplierIdNameAndPartsCountDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierIdNameAndPartsCountDto> suppliers) {
        this.suppliers = suppliers;
    }
}
