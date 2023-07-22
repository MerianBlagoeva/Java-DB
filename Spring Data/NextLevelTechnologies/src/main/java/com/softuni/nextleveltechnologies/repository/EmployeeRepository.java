package com.softuni.nextleveltechnologies.repository;

import com.softuni.nextleveltechnologies.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByFirstName(String firstName);

    List<Employee> findAllByAgeGreaterThan(Integer age);

}
