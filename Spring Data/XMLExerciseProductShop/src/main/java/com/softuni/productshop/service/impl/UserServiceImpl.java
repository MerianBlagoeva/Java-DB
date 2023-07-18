package com.softuni.productshop.service.impl;

import com.softuni.productshop.model.dto.UserSeedDto;
import com.softuni.productshop.model.dto.UserViewRootDto;
import com.softuni.productshop.model.dto.UserWithProductsDto;
import com.softuni.productshop.model.dto.ex4.ProductNameAndPriceAttributeDto;
import com.softuni.productshop.model.dto.ex4.SoldProductsDto;
import com.softuni.productshop.model.dto.ex4.UserCountDto;
import com.softuni.productshop.model.dto.ex4.UserWithProductsDtoEx4;
import com.softuni.productshop.model.entity.User;
import com.softuni.productshop.repository.UserRepository;
import com.softuni.productshop.service.UserService;
import com.softuni.productshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getCount() {
        return userRepository.count();
    }

    @Override
    public void seedUsers(List<UserSeedDto> users) {
        users
                .stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current()
                .nextLong(1, userRepository.count() + 1);

        return userRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public UserViewRootDto findUsersWithAtLeastOneSoldProduct() {
        UserViewRootDto userViewRootDto = new UserViewRootDto();

        userViewRootDto.setUsers(userRepository
                .findAllUsersWithAtLeastOneSoldProduct()
                .stream()
                .map(user -> modelMapper.map(user, UserWithProductsDto.class))
                .collect(Collectors.toList()));

        return userViewRootDto;
    }

    @Override
    public UserCountDto getAllUsersWithAtLeast1ProductSoldOrderByNumberOfProductsDesc() {
        UserCountDto userCountDto = new UserCountDto();
        List<User> users = userRepository.findAllWithAtLeast1SoldProductOrderByNumberOfProductsSold();

        userCountDto.setCount(users.size());

        userCountDto.setUsers(users
                .stream()
                .map(user -> {
                    UserWithProductsDtoEx4 userWithProductsDto = modelMapper.map(user, UserWithProductsDtoEx4.class);

                    userWithProductsDto.getProducts().setCount(user.getSoldProducts().size());

                    return userWithProductsDto;
                })
                .collect(Collectors.toList()));


        return userCountDto;
    }

}
