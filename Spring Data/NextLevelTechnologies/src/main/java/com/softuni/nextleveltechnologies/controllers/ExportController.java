package com.softuni.nextleveltechnologies.controllers;

import com.softuni.nextleveltechnologies.service.EmployeeService;
import com.softuni.nextleveltechnologies.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/export")
public class ExportController {


    private final ProjectService projectService;
    private final EmployeeService employeeService;

    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }


    @GetMapping("/project-if-finished")
    public String exportFinishedProjects(Model model){
        String projectsIfFinished = this.projectService
                .findFinishedProjectsInfo();

        model.addAttribute("projectsIfFinished", projectsIfFinished);

        return "export/export-project-if-finished";
    }

    @GetMapping("/employees-above")
    public String exportOldEmployees(Model model) {
        String employeesAbove = this.employeeService
                .findAllEmployeesWithAgeAbove(25);

        model.addAttribute("employeesAbove", employeesAbove);

        return "export/export-employees-with-age";
    }
}
