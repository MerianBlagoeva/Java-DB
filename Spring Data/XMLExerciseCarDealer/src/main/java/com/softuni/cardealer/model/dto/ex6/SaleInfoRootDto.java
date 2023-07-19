package com.softuni.cardealer.model.dto.ex6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleInfoRootDto {

    @XmlElement(name = "sale")
    private List<SaleInfoDto> sales;

    public List<SaleInfoDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleInfoDto> sales) {
        this.sales = sales;
    }
}
