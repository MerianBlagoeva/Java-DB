package com.softuni.productshop.model.dto;

import javax.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserViewRootDto {

    @XmlElement(name = "user")
    private List<UserWithProductsDto> users;


    public List<UserWithProductsDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductsDto> users) {
        this.users = users;
    }
}
