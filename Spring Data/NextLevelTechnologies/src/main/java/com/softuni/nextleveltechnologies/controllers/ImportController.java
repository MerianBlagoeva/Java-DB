package com.softuni.nextleveltechnologies.controllers;

import com.softuni.nextleveltechnologies.service.CompanyService;
import com.softuni.nextleveltechnologies.service.EmployeeService;
import com.softuni.nextleveltechnologies.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
public class ImportController extends BaseController {
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/import/xml")
    public String importXml(HttpServletRequest request, Model model) {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("areImported",
                new boolean[]{this.companyService.areImported(),
                this.projectService.areImported(),
                this.employeeService.areImported()});

        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String importCompanies(Model model, HttpServletRequest request) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("companies", companyService.readXmlFile());

        return "xml/import-companies";
    }
    @PostMapping("/import/companies")
    public String importCompaniesConfirm() throws JAXBException, IOException {
        System.out.println(this.companyService.importCompanies());

        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public String importProjects(Model model, HttpServletRequest request) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("projects", projectService.readXmlFile());

        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String importProjectsConfirm() throws JAXBException, IOException {
        System.out.println(this.projectService.importProjects());

        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public String importEmployees(Model model, HttpServletRequest request) throws IOException {
        if (!this.isLogged(request)) {
            return "redirect:/";
        }

        model.addAttribute("employees", employeeService.readXmlFile());

        return "xml/import-employees";
    }

    @PostMapping("/import/employees")
    public String importEmployeesConfirm() throws JAXBException, IOException {
        System.out.println(this.employeeService.importEmployees());

        return "redirect:/import/xml";
    }

}
