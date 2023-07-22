package com.softuni.nextleveltechnologies.controllers;

import com.softuni.nextleveltechnologies.dto.UserLoginDto;
import com.softuni.nextleveltechnologies.dto.UserRegisterDto;
import com.softuni.nextleveltechnologies.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/users/register")
    public String register(UserRegisterDto user, Model model) {
        if (userService.register(user)) {
            return "redirect:/users/login";
        }

        model.addAttribute("error", "There is an error");
        return "user/register";
    }

    @GetMapping("/users/login")
    public String login() {
        return "user/login";
    }
    
    @PostMapping("/users/login")
    public String login(UserLoginDto userLoginDto, Model model, HttpServletRequest request) {
        Long userId = userService.validateUserLoginDetails(userLoginDto);

        if (userId == null) {
            model.addAttribute("error", "There is an error");
            return "user/login";
        }

        request.getSession().setAttribute("userId", userId);

        return "redirect:/";

    }
}
