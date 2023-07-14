package com.softuni.productshop.model.dto;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserWithSoldProductWrapperDto {
    @Expose
    private Integer usersCount;
    @Expose
    List<UserWithSoldProductsDto> users;

    public UserWithSoldProductWrapperDto() {
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithSoldProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductsDto> users) {
        this.users = users;
    }
}
