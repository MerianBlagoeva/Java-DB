package com.example.springdataintro.services;

import com.example.springdataintro.repositories.StudentRepository;
import com.example.springdataintro.entities.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    
    private StudentRepository studentRepository;
    
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    
    @Override
    public Student register(String name) {
        Student student = new Student(name);

        return studentRepository.save(student);
    }

    @Override
    public Student searchByName(String name) {
        return studentRepository.findByName(name);
    }
}
