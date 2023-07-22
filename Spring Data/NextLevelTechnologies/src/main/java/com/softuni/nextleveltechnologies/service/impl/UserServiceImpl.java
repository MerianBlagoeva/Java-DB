package com.softuni.nextleveltechnologies.service.impl;

import com.softuni.nextleveltechnologies.dto.UserLoginDto;
import com.softuni.nextleveltechnologies.dto.UserRegisterDto;
import com.softuni.nextleveltechnologies.model.entity.User;
import com.softuni.nextleveltechnologies.repository.UserRepository;
import com.softuni.nextleveltechnologies.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUsernameOrEmail(
                userRegisterDto.getUsername(),
                userRegisterDto.getEmail()
        )) {
            return false;
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            return false;
        }

        User user = modelMapper.map(userRegisterDto, User.class);
        userRepository.save(user);

        return true;
    }

    @Override
    public Long validateUserLoginDetails(UserLoginDto userLoginDto) {

        User user = userRepository.findFirstByUsername(userLoginDto.getUsername());


        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            return null;
        }

        return user.getId();
    }

}
