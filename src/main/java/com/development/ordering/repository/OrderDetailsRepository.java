package com.development.ordering.repository;

import com.development.ordering.model.OrderDetails;
import com.development.ordering.model.OrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderDetailsRepository extends CrudRepository<OrderDetails, Long> {
    public List<OrderDetails> findAllByOrderStatus(OrderStatus o);
    public OrderDetails findOrderDetailsById(Long id);



    @Query(value =  "SELECT order_details.* FROM orders\n" +
                    "LEFT JOIN order_details on orders.id = order_details.order_id\n" +
                    "LEFT JOIN order_status on order_details.order_status_id = order_status.id\n" +
                    "WHERE orders.valid_from <= :date AND orders.valid_to >= :date AND order_status.english_name = :orderStatus " +
                    "AND order_details.order_text != ''",
            nativeQuery = true)
    public List<OrderDetails> getOrderDetails(@Param("date") Date date, @Param("orderStatus") String name);
}
