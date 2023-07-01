package com.example.springdataintro.services;

import com.example.springdataintro.entities.Student;

public interface StudentService {
    Student register(String name);

    Student searchByName(String name);
}
