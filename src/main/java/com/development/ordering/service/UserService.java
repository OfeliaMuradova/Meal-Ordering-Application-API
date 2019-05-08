package com.development.ordering.service;

import com.development.ordering.Globals;
import com.development.ordering.OrderingApplication;
import com.development.ordering.config.TokenProvider;
import com.development.ordering.model.LoginUser;
import com.development.ordering.model.User;
import com.development.ordering.model.UserDto;
import com.development.ordering.repository.UserRepository;
import com.development.ordering.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRoleRepository userRoleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Globals globals;

    private User loggedUser;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    //admin services

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public void delete(long id) {
        userRepository.removeById(id);
    }

    public void deleteUser(long id){
        userRepository.deleteUserById(id);
    }

//    public User findById(Long id) {
//        return userRepository.findUserById(id);
//    }

    public User userCreate(User user) throws Exception {
        user.setPassword(bCryptPasswordEncoder.encode("123qwe"));
        if (user.getUserRole() == null){
            user.setUserRole(userRoleRepository.findByName("USER"));
        }
        try{
            return userRepository.save(user);
        }
        catch (Exception e){
            throw e;
        }

    }

    //user services
    public User userSave(User user) throws Exception {
        try{
            return userRepository.save(user);
        }
        catch (Exception e){
            throw e;
        }
    }

    public User changePassword(String oldPassword, String newPassword, Long id) throws Exception {
        loggedUser = globals.getCurrentUser();
        if (!loggedUser.getId().equals(id))
            throw new Exception("You have no permission, good bye :))");
        if (!this.bCryptPasswordEncoder.matches(oldPassword, loggedUser.getPassword()))
            throw new Exception("current password is invalid");
        loggedUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(loggedUser);

        return login(new LoginUser(loggedUser.getUsername(), newPassword));
    }

    public User login(LoginUser loginUser) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        User user = getUserByUsername(loginUser.getUsername());
        user.setToken(token);
        return user;
    }

    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User convertToEntity(UserDto userDto) throws Exception {
        User user = modelMapper.map(userDto, User.class);
        loggedUser = globals.getCurrentUser();

        if (userDto.getId() != null) {
            User oldUser = getUserById(userDto.getId());
            //BeanUtils.copyProperties(userDto, oldUser, "password", "userRole");  //other way
            user.setPassword(oldUser.getPassword());
            user.setId(oldUser.getId());
            if (userDto.getUserRole() == null){
                user.setUserRole(oldUser.getUserRole());
            }

            if (validateUserPermission(userDto) || validateAdminPermission(oldUser))
                throw new Exception("You have no permission, good bye :))");

        }
        return user;
    }

    private boolean validateUserPermission(UserDto userDto){
        return !loggedUser.getUserRole().getName().equals("ADMIN") && (userDto.getUserRole() != null || !loggedUser.getId().equals(userDto.getId()));
    }

    private boolean validateAdminPermission(User oldUser){
        return loggedUser.getUserRole().getName().equals("ADMIN") && oldUser.getUserRole().getName().equals("ADMIN") && !loggedUser.getId().equals(oldUser.getId());
    }
}
