package com.softuni.nextleveltechnologies.service.impl;

import com.softuni.nextleveltechnologies.dto.ProjectSeedRootDto;
import com.softuni.nextleveltechnologies.model.entity.Project;
import com.softuni.nextleveltechnologies.repository.ProjectRepository;
import com.softuni.nextleveltechnologies.service.CompanyService;
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
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static final String PROJECTS_FILE_PATH = "src/main/resources/files/xmls/projects.xml";
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CompanyService companyService;

    public ProjectServiceImpl(ProjectRepository projectRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, CompanyService companyService) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.companyService = companyService;
    }

    @Override
    public boolean areImported() {
        return projectRepository.count() > 0;
    }

    @Override
    public String readXmlFile() throws IOException {
        return Files.readString(Path.of(PROJECTS_FILE_PATH));
    }

    @Override
    public String importProjects() throws JAXBException, FileNotFoundException {
        StringBuilder output = new StringBuilder();

        xmlParser.fromFile(PROJECTS_FILE_PATH, ProjectSeedRootDto.class)
                .getProjects()
                .stream()
                .filter(projectSeedDto -> {
                    boolean isValid = validationUtil.isValid(projectSeedDto);

                    output.append(isValid ? String.format("Successfully imported project %s",
                                    projectSeedDto.getName())
                                    : "Invalid project")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(projectSeedDto -> {
                    Project project = modelMapper.map(projectSeedDto, Project.class);
                    project.setCompany(companyService.findByName(projectSeedDto.getCompany().getName()));

                    return project;
                })
                .forEach(projectRepository::save);

        return output.toString().trim();
    }

    @Override
    public Project findByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    public String findFinishedProjectsInfo() {
        StringBuilder result = new StringBuilder();

        projectRepository.findAllByIsFinishedIsTrue()
                .forEach(project -> result
                        .append(String.format("Project Name: %s\n" +
                                "\tDescription: %s\n" +
                                "\t%.2f\n",
                                project.getName(),
                                project.getDescription(),
                                project.getPayment())));

        return result.toString().trim();
    }
}
