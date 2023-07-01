package com.example.springdataintro;

import com.example.springdataintro.entities.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {

    Student findByName(String name);
}
