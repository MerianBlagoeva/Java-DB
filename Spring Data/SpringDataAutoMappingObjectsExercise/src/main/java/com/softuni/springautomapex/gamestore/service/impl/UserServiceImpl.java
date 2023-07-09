package com.softuni.springautomapex.gamestore.service.impl;

import com.softuni.springautomapex.gamestore.model.dto.UserLoginDto;
import com.softuni.springautomapex.gamestore.model.dto.UserRegisterDto;
import com.softuni.springautomapex.gamestore.model.entity.Game;
import com.softuni.springautomapex.gamestore.model.entity.User;
import com.softuni.springautomapex.gamestore.repository.GameRepository;
import com.softuni.springautomapex.gamestore.repository.UserRepository;
import com.softuni.springautomapex.gamestore.service.UserService;
import com.softuni.springautomapex.gamestore.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User currentUser;

    private final GameRepository gameRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gameRepository = gameRepository;
    }

    @Override
    public void register(UserRegisterDto userRegisterDto) {
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
        if (userRepository.count() == 1) {
            user.setAdmin(true);
        }

        System.out.println("User is admin: " + user.isAdmin());

    }

    @Override
    public void login(UserLoginDto userLoginDto) {
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

        currentUser = user;
        System.out.println("Successfully logged in " + user.getFullName());
    }

    @Override
    public void logout() {
        if (currentUser != null) {
            System.out.printf("User %s successfully logged out%n", currentUser.getFullName());
            currentUser = null;
        } else {
            System.out.println("Cannot logout. No user was logged in.");
        }
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser.isAdmin();
    }
}
