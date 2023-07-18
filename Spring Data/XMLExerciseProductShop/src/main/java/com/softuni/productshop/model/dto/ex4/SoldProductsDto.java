package com.softuni.productshop.model.dto.ex4;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsDto {

    @XmlAttribute(name = "count")
    private Integer count;

    @XmlElement(name = "product")
    private List<ProductNameAndPriceAttributeDto> products;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductNameAndPriceAttributeDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNameAndPriceAttributeDto> products) {
        this.products = products;
    }
}
