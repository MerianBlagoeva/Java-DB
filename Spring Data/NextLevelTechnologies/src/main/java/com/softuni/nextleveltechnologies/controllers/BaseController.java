package com.softuni.nextleveltechnologies.controllers;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {

    protected boolean isLogged(HttpServletRequest request) {
        Object userId = request.getSession().getAttribute("userId");

        return userId != null;
    }
}
