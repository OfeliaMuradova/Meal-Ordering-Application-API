package com.development.ordering.service;

import com.development.ordering.Globals;
import com.development.ordering.model.Menu;
import com.development.ordering.model.Order;
import com.development.ordering.model.OrderDetails;
import com.development.ordering.model.User;
import com.development.ordering.repository.MenuRepository;
import com.development.ordering.repository.OrderDetailsRepository;
import com.development.ordering.repository.OrderRepository;
import com.development.ordering.repository.OrderStatusRepository;
import com.development.ordering.service.admin.OrderDetailsService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private Globals globals;

    private User loggedUser;
    
    public Order addOrUpdateOrder(Order order) throws Exception {
        loggedUser = globals.getCurrentUser();
        order.setUser(loggedUser);
        final String[] error = {""};
        order.getOrderDetails().forEach(orderDetail -> {
            OrderDetails oldOrderDetail = orderDetailsService.getOrderDetail(orderDetail.getId());
            if (oldOrderDetail == null || (oldOrderDetail.getOrderStatus().getEnglishName().equals("Cancelled") && !oldOrderDetail.getOrderText().equals(orderDetail.getOrderText())))
                orderDetail.setOrderStatus(orderStatusRepository.getOrderStatusByEnglishName("Pending"));

            if (oldOrderDetail != null && oldOrderDetail.getOrderStatus().getEnglishName().equals("Confirmed")){
                error[0] = "Order is already confirmed and cant be changed";
            }
        });

        if (!error[0].equals(""))
            throw new Exception(error[0]);

        return orderRepository.save(order);
    }

    public Order getOrder(String week) {
        Date date = week.equals("current") ? new Date() : DateUtils.addDays(new Date(), 7);
        loggedUser = globals.getCurrentUser();
        return orderRepository.getOrderByUser(loggedUser.getId(), date);
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }

    public List<Menu> getMenus(String week) {
        Date date = week.equals("current") ? new Date() : DateUtils.addDays(new Date(), 7);
        return menuRepository.getMenuByWeek(date);
    }
}
