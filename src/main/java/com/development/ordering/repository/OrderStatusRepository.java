package com.development.ordering.repository;

import com.development.ordering.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatus, Long> {
    public OrderStatus getOrderStatusByEnglishName(String name);
}
