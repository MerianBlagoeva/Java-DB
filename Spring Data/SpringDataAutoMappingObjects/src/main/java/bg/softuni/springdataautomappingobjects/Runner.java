package bg.softuni.springdataautomappingobjects;

import bg.softuni.springdataautomappingobjects.dto.ManagerDto;
import bg.softuni.springdataautomappingobjects.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {
    private final EmployeeService employeeService;

    public Runner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
//        ManagerDto managerDto = this.employeeService.findOne(1L);

        List<ManagerDto> managers = this.employeeService.findAll();
        managers.forEach(managerDto -> {
            System.out.println(managerDto.getFirstName() + " " + managerDto.getLastName() + " : ");
            managerDto.getSubordinates().forEach(employeeDto -> {
                System.out.println("\t" + employeeDto.getFirstName() + " " + employeeDto.getLastName() + " : " + employeeDto.getIncome());
            });
        });

    }
}
