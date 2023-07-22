package com.softuni.nextleveltechnologies.service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface EmployeeService {
    boolean areImported();

    String readXmlFile() throws IOException;

    String importEmployees() throws JAXBException, FileNotFoundException;

    String findAllEmployeesWithAgeAbove(Integer age);
}
