package com.development.ordering.config;

import com.development.ordering.model.OrderStatus;
import com.development.ordering.model.User;
import com.development.ordering.model.UserRole;
import com.development.ordering.model.WeekDays;
import com.development.ordering.repository.OrderStatusRepository;
import com.development.ordering.repository.WeekDaysRepository;
import com.development.ordering.service.UserRoleService;
import com.development.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class InitData {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private WeekDaysRepository weekDaysRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public InitData(){

    }

    @Bean
    public boolean insertTestData(){
        try{
            weekDaysRepository.save(new WeekDays("Monday", "Monday"));
            weekDaysRepository.save(new WeekDays("Tuesday", "Tuesday"));
            weekDaysRepository.save(new WeekDays("Wednesday", "Wednesday"));
            weekDaysRepository.save(new WeekDays("Thursday", "Thursday"));
            weekDaysRepository.save(new WeekDays("Friday", "Friday"));
            orderStatusRepository.save(new OrderStatus("Pending", "Pending"));
            orderStatusRepository.save(new OrderStatus("Confirmed", "Confirmed"));
            orderStatusRepository.save(new OrderStatus("Cancelled", "Cancelled"));
            userRoleService.addOrUpdateUserRole(new UserRole("ADMIN"));
            userRoleService.addOrUpdateUserRole(new UserRole("USER"));
            userService.userSave(new User("admin", "admin", "admin@gmail.com", "11211", "admin",  bCryptPasswordEncoder.encode("admin"), userRoleService.getUserRoleByName("ADMIN")));
            userService.userSave(new User("user","user", "user@gmail.com", "11211", "user", bCryptPasswordEncoder.encode("admin"), userRoleService.getUserRoleByName("USER")));
            return true;
        }
        catch (Exception e){
            System.out.println("error while inserting data " + e.toString());
            return false;
        }
    }
}
