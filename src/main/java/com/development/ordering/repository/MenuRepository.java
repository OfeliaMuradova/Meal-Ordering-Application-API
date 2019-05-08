package com.development.ordering.repository;

import com.development.ordering.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MenuRepository extends CrudRepository<Menu, Long> {
    public Menu findMenuById(Long id);
    public int deleteMenuById(long id);

    @Query(value = "SELECT * " +
                   "FROM menus " +
                   "WHERE :date >= valid_from AND :date <= valid_to",
            nativeQuery = true)
    List<Menu> getMenuByWeek(@Param("date") Date date);
}
