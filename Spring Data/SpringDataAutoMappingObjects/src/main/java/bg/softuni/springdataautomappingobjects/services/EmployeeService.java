package bg.softuni.springdataautomappingobjects.services;

import bg.softuni.springdataautomappingobjects.dto.EmployeeDto;
import bg.softuni.springdataautomappingobjects.dto.ManagerDto;

import java.util.List;

public interface EmployeeService {
    ManagerDto findOne(Long id);

    List<ManagerDto> findAll();
}
