package com.softuni.nextleveltechnologies.service.impl;

import com.softuni.nextleveltechnologies.dto.CompanySeedRootDto;
import com.softuni.nextleveltechnologies.model.entity.Company;
import com.softuni.nextleveltechnologies.repository.CompanyRepository;
import com.softuni.nextleveltechnologies.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {
    private static final String COMPANIES_FILE_PATH = "src/main/resources/files/xmls/companies.xml";
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return companyRepository.count() > 0;
    }

    @Override
    public String readXmlFile() throws IOException {
        return Files.readString(Path.of(COMPANIES_FILE_PATH));
    }

    @Override
    public String importCompanies() throws JAXBException, FileNotFoundException {
        StringBuilder output = new StringBuilder();

        xmlParser.fromFile(COMPANIES_FILE_PATH, CompanySeedRootDto.class)
                .getCompanies()
                .stream()
                .filter(companySeedDto -> {
                    boolean isValid = validationUtil.isValid(companySeedDto)
                            && companyRepository.findByName(companySeedDto.getName()).isEmpty();

                    output.append(isValid ? String.format("Successfully imported company %s",
                                    companySeedDto.getName())
                                    : "Invalid company")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(companySeedDto -> modelMapper.map(companySeedDto, Company.class))
                .forEach(companyRepository::save);

        return output.toString().trim();

    }

    @Override
    public Company findByName(String name) {
        return companyRepository.findByName(name).orElse(null);
    }
}
