package com.softuni.cardealer.repository;

import com.softuni.cardealer.model.dto.ex5.CustomerWithBoughtCarsDto;
import com.softuni.cardealer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByOrderByBirthDateAscIsYoungDriverDesc();

    @Query("SELECT new com.softuni.cardealer.model.dto.ex5.CustomerWithBoughtCarsDto(c.name, COUNT(s.car.id), SUM(car.price)) " +
            "FROM Customer c " +
            "JOIN Sale s ON s.customer.id = c.id " +
            "JOIN Car car ON car.id = s.car.id " +
            "WHERE c.id = s.customer.id " +
            "GROUP BY s.customer.id "+
            "ORDER BY SUM(car.price) DESC, COUNT(s.car.id) DESC ")
    List<CustomerWithBoughtCarsDto> findAllWithAtLeastOneBoughtCarOrderByTotalMoneySpendDescThenByBoughtCarsDesc();


//    @Query("SELECT c FROM Customer c " +
//            "join Sale s ON s.customer.id = c.id " +
//            "join s.car.parts p " +
//            "WHERE size(c.sales) > 0 " +
//            "GROUP BY  c.id " +
//            "ORDER BY size(c.sales), SUM(p.price)")
//    List<Customer> findAllWithAtLeastOneBoughtCarOrderByTotalMoneySpendDescThenByBoughtCarsDesc();
}
