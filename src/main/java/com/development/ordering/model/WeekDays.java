package com.development.ordering.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "week_days")
public class WeekDays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String day;
    @Column(unique = true)
    private String englishName;

//    @OneToMany
//    public List<Menu> menus;
//
//    @OneToMany
//    public List<OrderDetails> orderDetails;
//
//    @JsonIgnoreProperties("weekDays")
//    @ManyToMany(cascade = {CascadeType.MERGE})
//    private List<Company> companies;

    public WeekDays(){}

    public WeekDays(String day, String englishName) {
        this.day = day;
        this.englishName = englishName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getEnglishName() {
        return englishName;
    }

//    public List<Company> getCompanies() {
//        return companies;
//    }
//
//    public void setCompanies(List<Company> companies) {
//        this.companies = companies;
//    }
}
