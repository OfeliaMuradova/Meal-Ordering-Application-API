package com.development.ordering.model;

import com.development.ordering.repository.OrderStatusRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang.SerializationUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name="order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderText;

    @CreationTimestamp
    @Column(nullable = false, updatable=false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @JsonIgnoreProperties("orderDetails")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "confirmer_id")
    private User confirmer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "week_day_id", nullable = false)
    private WeekDays weekDay;

    @Transient
    private transient Object oldObject;

    public OrderDetails() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderText() {
        return orderText;
    }

    public void setOrderText(String orderText) {
        this.orderText = orderText;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public User getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(User confirmer) {
        this.confirmer = confirmer;
    }

    public WeekDays getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDays weekDay) {
        this.weekDay = weekDay;
    }

//    @PostLoad
//    private void postLoad(){
//        this.oldObject = SerializationUtils.clone(this);
//    }
//
//    @PreUpdate
//    private void preUpdate() throws Exception {
//        OrderDetails orderDetails = (OrderDetails) this.oldObject;
//        if (orderDetails.getOrderStatus().getEnglishName().equals("Confirmed"))
//            throw new Exception("Cant change order for this day, order is already confirmed");
//    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
