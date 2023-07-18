package com.softuni.productshop.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryViewRootDto {

    @XmlElement(name = "category")
    private List<CategorySummaryDto> categories;

    public List<CategorySummaryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorySummaryDto> categories) {
        this.categories = categories;
    }
}
