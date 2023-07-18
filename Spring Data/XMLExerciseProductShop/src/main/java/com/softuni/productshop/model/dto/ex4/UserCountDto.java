package com.softuni.productshop.model.dto.ex4;

import com.softuni.productshop.model.dto.UserSeedDto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserCountDto {

    @XmlAttribute(name = "count")
    private Integer count;
    @XmlElement(name = "user")
    private List<UserWithProductsDtoEx4> users;


    public List<UserWithProductsDtoEx4> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithProductsDtoEx4> users) {
        this.users = users;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
