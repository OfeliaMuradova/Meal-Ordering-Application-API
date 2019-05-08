package com.development.ordering.service.admin;

import com.development.ordering.model.OrderDetails;
import com.development.ordering.model.OrderStatus;
import com.development.ordering.repository.OrderDetailsRepository;
import com.development.ordering.repository.OrderStatusRepository;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderDetailsService {

    @Autowired
    public OrderDetailsRepository orderDetailsRepository;

    @Autowired
    public OrderStatusRepository orderStatusRepository;

    public List<OrderDetails> getAllNeededOrders(String name, String week) {
        Date date = getWeekDate(week);
        //OrderStatus orderStatus = orderStatusRepository.getOrderStatusByEnglishName(name);
        return new ArrayList<>(orderDetailsRepository.getOrderDetails(date, name));
    }

    public OrderDetails getOrderDetail(Long id){
        return orderDetailsRepository.findOrderDetailsById(id);
    }

    public OrderDetails setStatus(Long id, String name) {
        OrderDetails orderDetail = getOrderDetail(id);
        OrderStatus orderStatus = orderStatusRepository.getOrderStatusByEnglishName(name);
        orderDetail.setOrderStatus(orderStatus);
        return orderDetailsRepository.save(orderDetail);
    }

    public Map<String, Integer> getSumOfTheMeals(String week){
        Date date = getWeekDate(week);
        List<OrderDetails> orderDetails = new ArrayList<>(orderDetailsRepository.getOrderDetails(date, "Confirmed"));
        Map<String, Integer> dictionary = new HashMap<>();
        orderDetails.forEach(orderDetail -> {
           String orderText = orderDetail.getOrderText();
            for (String s : orderText.split(",\\s*")) {
                if(dictionary.get(s) != null){
                    dictionary.replace(s, dictionary.get(s) + 1);
                }
                else{
                    dictionary.put(s, 1);
                }
            }
        });

        return dictionary;
    }

    private Date getWeekDate(String week) {
        return week.equals("current") ? new Date() : DateUtils.addDays(new Date(), 7);
    }
}