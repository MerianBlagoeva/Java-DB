package com.softuni.nextleveltechnologies.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectSeedRootDto {

    @XmlElement(name = "project")
    private List<ProjectSeedDto> projects;

    public List<ProjectSeedDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectSeedDto> projects) {
        this.projects = projects;
    }
}
