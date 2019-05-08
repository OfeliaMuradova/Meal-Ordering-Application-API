package com.development.ordering.repository;

import com.development.ordering.model.Order;
import com.development.ordering.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    public Order getOrderById(Long id);

    @Query(value = "SELECT * " +
                   "FROM orders " +
                   "WHERE user_id = :user_id AND :orderDate >= valid_from AND :orderDate <= valid_to",
           nativeQuery = true)
    public Order getOrderByUser(@Param("user_id") long user_id, @Param("orderDate") Date orderDate);
}
