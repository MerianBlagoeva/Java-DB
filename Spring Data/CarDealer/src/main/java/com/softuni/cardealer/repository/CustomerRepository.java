package com.softuni.cardealer.repository;

import com.softuni.cardealer.model.dto.CustomerInfoDto;
import com.softuni.cardealer.model.dto.CustomerWithBoughtCarsDto;
import com.softuni.cardealer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByOrderByBirthDateAscIsYoungDriverDesc();

//    @Query("SELECT new com.softuni.cardealer.model.dto.CustomerWithBoughtCarsDto(c.name, COUNT(s.car.id), SUM(cp.price)) " +
//            "FROM Customer c " +
//            "JOIN Sale s ON s.customer.id = c.id " +
//            "JOIN Car car ON car.id = s.car.id " +
//            "JOIN car.parts cp " +
//            "WHERE c.id = s.customer.id " +
//            "GROUP BY c.id "+
//            "ORDER BY SUM(cp.price) DESC , COUNT(s.car.id) DESC ")
//    List<CustomerWithBoughtCarsDto> findAllWithAtLeastOneBoughtCarOrderByTotalMoneySpendDescThenByBoughtCarsDesc();


    @Query("SELECT c FROM Customer c " +
            "join Sale s ON s.customer.id = c.id " +
            "join s.car.parts p " +
            "WHERE size(c.sales) > 0 " +
            "GROUP BY  c.id " +
            "ORDER BY size(c.sales), SUM(p.price)")
    List<Customer> findAllWithAtLeastOneBoughtCarOrderByTotalMoneySpendDescThenByBoughtCarsDesc();
}
