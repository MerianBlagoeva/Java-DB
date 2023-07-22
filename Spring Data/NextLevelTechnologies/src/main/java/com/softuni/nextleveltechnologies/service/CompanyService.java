package com.softuni.nextleveltechnologies.service;

import com.softuni.nextleveltechnologies.model.entity.Company;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface CompanyService {

    boolean areImported();

    String readXmlFile() throws IOException;

    String importCompanies() throws JAXBException, FileNotFoundException;

    Company findByName(String name);
}
