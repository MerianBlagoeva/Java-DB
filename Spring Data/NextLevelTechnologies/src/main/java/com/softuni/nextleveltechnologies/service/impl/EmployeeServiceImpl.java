package com.softuni.nextleveltechnologies.service.impl;

import com.softuni.nextleveltechnologies.dto.EmployeeSeedRootDto;
import com.softuni.nextleveltechnologies.model.entity.Employee;
import com.softuni.nextleveltechnologies.repository.EmployeeRepository;
import com.softuni.nextleveltechnologies.service.EmployeeService;
import com.softuni.nextleveltechnologies.service.ProjectService;
import com.softuni.nextleveltechnologies.util.ValidationUtil;
import com.softuni.nextleveltechnologies.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEES_FILE_PATH = "src/main/resources/files/xmls/employees.xml";
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final ProjectService projectService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, ProjectService projectService) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.projectService = projectService;
    }

    @Override
    public boolean areImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEES_FILE_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder output = new StringBuilder();

        xmlParser.fromFile(EMPLOYEES_FILE_PATH, EmployeeSeedRootDto.class)
                .getEmployees()
                .stream()
                .filter(employeeSeedDto -> {
                    boolean isValid = validationUtil.isValid(employeeSeedDto);

                    output.append(isValid ? String.format("Successfully imported employee %s %s",
                                    employeeSeedDto.getFirstName(), employeeSeedDto.getLastName())
                                    : "Invalid employee")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(employeeSeedDto -> {
                    Employee employee = modelMapper.map(employeeSeedDto, Employee.class);
                    employee.setProject(projectService.findByName(employeeSeedDto.getProject().getName()));

                    return employee;
                })
                .forEach(employeeRepository::save);

        return output.toString().trim();
    }

    @Override
    public String findAllEmployeesWithAgeAbove(Integer age) {
        StringBuilder result = new StringBuilder();

        employeeRepository.findAllByAgeGreaterThan(age)
                .forEach(employee -> result
                        .append(String.format("""
                                        Name: %s %s
                                        \tAge: %d
                                        \tProject name: %s
                                        """,
                                employee.getFirstName(), employee.getLastName(),
                                employee.getAge(),
                                employee.getProject().getName())));

        return result.toString().trim();
    }
}
