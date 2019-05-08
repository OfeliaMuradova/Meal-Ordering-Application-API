package com.development.ordering.service.admin;

import com.development.ordering.model.Menu;
import com.development.ordering.repository.MenuRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    private EntityManager em;

    public List<Menu> getAllMenus(){
        List<Menu> menus = new ArrayList<>();
        menuRepository.findAll().forEach(menus::add);
        return menus;
    }

    public Menu getMenu(long id) throws ResourceNotFoundException{
        try {
            return menuRepository.findMenuById(id);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Menu not found with id ::" + id);
        }
    }

    public Menu addOrUpdateMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public boolean deleteMenu(long id){
        try{
            menuRepository.deleteMenuById(id);
            return true;
        }catch (Exception e){
            throw e;
        }
    }
}
