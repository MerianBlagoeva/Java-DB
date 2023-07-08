package com.softuni.springdataautomappingobjectsexercise.dtoEx.service.impl;

import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.UserLoginDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.dto.UserRegisterDto;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.model.entity.User;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.repository.UserRepository;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.service.UserService;
import com.softuni.springdataautomappingobjectsexercise.dtoEx.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            System.out.println("Wrong confirm password");
        }

        Set<ConstraintViolation<UserRegisterDto>> violations =
                validationUtil.getViolations(userRegisterDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User user = modelMapper.map(userRegisterDto, User.class);

        userRepository.save(user);
        System.out.println(user.getFullName() + " was registered");

    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violations = validationUtil.getViolations(userLoginDto);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = userRepository.findByEmailAndPassword(
                        userLoginDto.getEmail(), userLoginDto.getPassword())
                .orElse(null);

        if (user == null) {
            System.out.println("Incorrect username / password");
            return;
        }

        loggedInUser = user;
        System.out.println("Successfully logged in " + user.getFullName());
    }

    @Override
    public void logout() {
        if (loggedInUser != null) {
            System.out.printf("User %s successfully logged out%n", loggedInUser.getFullName());
            loggedInUser = null;
        } else {
            System.out.println("Cannot logout. No used was logged in.");
        }
    }
}
