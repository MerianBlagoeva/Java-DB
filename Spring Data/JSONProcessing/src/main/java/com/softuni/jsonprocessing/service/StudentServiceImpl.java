package com.softuni.jsonprocessing.service;


import com.google.gson.Gson;
import com.softuni.jsonprocessing.dto.StudentDTO;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl {

    private Gson gson;

    public StudentServiceImpl(Gson gson) {
        this.gson = gson;
    }

    public void create(String createJson) {
        StudentDTO studentDTO = gson.fromJson(createJson, StudentDTO.class);

        System.out.println("Created -> " + studentDTO);
    }

    public String getAllAsJson() {
        return null;
    }
}
