package com.development.ordering.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "companies")
@DynamicUpdate
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "webpage_url")
    private String webPageUrl;

    //@JsonIgnoreProperties("company")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "company")
    //@JoinColumn(name = "company_id", nullable=false)
    private Set<Menu> menus;

    @JsonIgnoreProperties("company")
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    @JoinTable(name = "companies_week_days",
            joinColumns = { @JoinColumn(name = "company_id") },
            inverseJoinColumns = { @JoinColumn(name = "week_days_id") })
    private List<WeekDays> weekDays;

    @PrePersist
    public void prePersist(){
        if (menus != null) menus.forEach(menu -> menu.setCompany(this));
    }

    public Company() {
    }

    public Company(String name, String webpage_url) {
        this.name = name;
        this.webPageUrl = webpage_url;
    }

    public Company(String name, String webpage_url, Set<Menu> menus, List<WeekDays> weekDays){
        this.name = name;
        this.webPageUrl = webpage_url;
        this.menus = menus;
        this.weekDays = weekDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebPageUrl() {
        return webPageUrl;
    }

    public void setWebPageUrl(String web_page_url) {
        this.webPageUrl = web_page_url;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public List<WeekDays> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<WeekDays> weekDays) {
        this.weekDays = weekDays;
    }
}
