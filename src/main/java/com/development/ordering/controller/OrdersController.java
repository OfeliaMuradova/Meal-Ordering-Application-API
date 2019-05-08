package com.development.ordering.controller;

import com.development.ordering.model.Menu;
import com.development.ordering.model.Order;
import com.development.ordering.model.WeekDays;
import com.development.ordering.repository.WeekDaysRepository;
import com.development.ordering.service.OrdersService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private WeekDaysRepository weekDaysRepository;

    @RequestMapping(method=RequestMethod.POST, value="/")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) throws Exception {
        return ResponseEntity.ok().body(ordersService.addOrUpdateOrder(order));
    }

    @RequestMapping(method=RequestMethod.GET, value="/edit")
    public ResponseEntity<Order> getOrder(@RequestParam String week) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(ordersService.getOrder(week));
    }

    @RequestMapping(method=RequestMethod.GET, value="/orderMenus")
    public ResponseEntity<List<Menu>> getMenus(@RequestParam String week) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(ordersService.getMenus(week));
    }

    @RequestMapping(method=RequestMethod.PUT, value="/{id}")
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody Order order, @PathVariable(value = "id") long id) throws Exception {
        return  ResponseEntity.ok().body(ordersService.addOrUpdateOrder(order));
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        ordersService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(method=RequestMethod.GET, value="/weekDays")
    public List<WeekDays> getWeekDays() throws ResourceNotFoundException {
        return (List<WeekDays>) weekDaysRepository.findAll();
    }
}
