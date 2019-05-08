package com.development.ordering.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.annotation.PreDestroy;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String path;

    private Integer week_num;

    @Column(nullable = false, unique=true)
    private Date validFrom;

    @Column(nullable = false, unique=true)
    private Date validTo;

    //@JsonManagedReference
    @JsonIgnoreProperties("menus")
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Menu() { }

    public Menu(String path, Integer week_num) {
        this.setPath(path);
        this.setWeekNum(week_num);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getWeekNum() {
        return week_num;
    }

    public void setWeekNum(Integer week_num) {
        this.week_num = week_num;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @PrePersist
    public void prePersist() throws Exception {

        //this.validTo.toLocalDate().toEpochDay() - this.validFrom.toLocalDate().toEpochDay() != 4

        if (!this.validFrom.toLocalDate().getDayOfWeek().toString().equals("MONDAY") || !this.validTo.toLocalDate().getDayOfWeek().toString().equals("SUNDAY"))
            throw new Exception("Wrong Dates");
    }
}
