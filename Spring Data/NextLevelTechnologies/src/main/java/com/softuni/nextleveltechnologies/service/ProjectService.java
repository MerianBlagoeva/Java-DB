package com.softuni.nextleveltechnologies.service;

import com.softuni.nextleveltechnologies.model.entity.Project;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ProjectService {
    boolean areImported();

    String readXmlFile() throws IOException;
    String importProjects() throws JAXBException, FileNotFoundException;

    Project findByName(String name);

    String findFinishedProjectsInfo();
}
