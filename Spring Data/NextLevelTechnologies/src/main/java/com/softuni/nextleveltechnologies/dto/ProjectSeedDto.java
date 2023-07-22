package com.softuni.nextleveltechnologies.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectSeedDto {

    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "start-date")
    private String startDate;
    @XmlElement(name = "is-finished")
    private Boolean isFinished;
    @XmlElement(name = "payment")
    private BigDecimal payment;
    @XmlElement
    private CompanySeedDto company;

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    @NotNull
    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @NotNull
    public CompanySeedDto getCompany() {
        return company;
    }

    public void setCompany(CompanySeedDto company) {
        this.company = company;
    }
}
