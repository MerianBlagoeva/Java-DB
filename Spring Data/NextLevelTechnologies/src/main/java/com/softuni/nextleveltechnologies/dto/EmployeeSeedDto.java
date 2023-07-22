package com.softuni.nextleveltechnologies.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeSeedDto {

    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement(name = "age")
    private Integer age;
    @XmlElement(name = "project")
    private ProjectSeedDto project;

    @NotBlank
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @NotBlank
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @NotNull
    public ProjectSeedDto getProject() {
        return project;
    }

    public void setProject(ProjectSeedDto project) {
        this.project = project;
    }
}
