package com.example.details_shop.web;

import com.example.details_shop.entity.User;
import com.example.details_shop.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Transactional
public class UserController implements Serializable {
    @EJB
    private UserService userService;

    private User user = new User();
    private List<User> users;

    @PostConstruct
    public void init() {
        try {
            users = userService.showAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createUser() {
        userService.create(user);
        user = new User();
        users = userService.showAll();
        return "users";
    }

    public String deleteUser(Long userId) {
        userService.delete(userId);
        users = userService.showAll();
        return "users";
    }

    public String editUser(Long userId) {
        User userForEdit = userService.show(userId);
        if (userForEdit != null) {
            this.user = userForEdit;
        }
        return "editUser";
    }

    public String updateUser() {
        userService.update(user);
        user = new User();
        users = userService.showAll();
        return "users";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
