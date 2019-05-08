package com.development.ordering.controller.admin;

import com.development.ordering.model.OrderDetails;
import com.development.ordering.service.admin.OrderDetailsService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin/orders")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService ordersService;

    @RequestMapping(method= RequestMethod.GET, value="/list")
    public ResponseEntity<List<OrderDetails>> getAllPendingOrders(@RequestParam String name, @RequestParam String week) {
        return ResponseEntity.ok().body(ordersService.getAllNeededOrders(name, week));
    }

    @RequestMapping(method= RequestMethod.POST, value="/set_status/{id}")
    public OrderDetails setStatus(@RequestParam String name, @PathVariable Long id){
        return ordersService.setStatus(id, name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getOrdersSum")
    public Map<String, Integer> getOrdersSum(@RequestParam String week) throws ResourceNotFoundException {
        return ordersService.getSumOfTheMeals(week);
    }
}
