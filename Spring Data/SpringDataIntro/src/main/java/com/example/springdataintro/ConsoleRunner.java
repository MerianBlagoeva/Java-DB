package com.example.springdataintro;

import com.example.springdataintro.entities.Student;
import com.example.springdataintro.services.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private StudentService studentService;

    public ConsoleRunner(StudentService studentService) {
        this.studentService = studentService;
    }


    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String studentName = sc.nextLine();

        Student register = studentService.register(studentName);

        Student student = studentService.searchByName(studentName);

        System.out.println(student.getId());
    }
}
