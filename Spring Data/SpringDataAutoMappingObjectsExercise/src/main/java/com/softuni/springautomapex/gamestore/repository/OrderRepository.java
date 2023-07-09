package com.softuni.springautomapex.gamestore.repository;

import com.softuni.springautomapex.gamestore.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
