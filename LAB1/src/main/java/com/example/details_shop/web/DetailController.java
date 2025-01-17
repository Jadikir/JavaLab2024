package com.example.details_shop.web;

import com.example.details_shop.entity.Detail;
import com.example.details_shop.entity.User;
import com.example.details_shop.service.DetailService;
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
public class DetailController implements Serializable {
    @EJB
    private DetailService detailService;

    @EJB
    private UserService userService;

    Detail detail = new Detail();

    private List<Detail> details;
    private List<User> users;

    private Long selectedUserId;

    @PostConstruct
    public void init() {
        try {
            details = detailService.showAll();
            users = userService.showAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createDetail() {
        User user = userService.show(selectedUserId);
        if (user != null) {
            detail.setUser(user);
            detailService.create(detail);
            detail = new Detail();
            details = detailService.showAll();
            return "index";
        }
        return null;
    }

    public String deleteDetail(Long id) {
        detailService.delete(id);
        details = detailService.showAll();
        return "index";
    }

    public String editDetail(Long id) {
        Detail detailForEdit = detailService.show(id);
        if (detailForEdit != null) {
            this.detail = detailForEdit;
        }
        users = userService.showAll();
        return "edit";
    }

    public String updateDetail() {
        User user = userService.show(selectedUserId);
        if (user != null) {
            detail.setUser(user);
            detailService.update(detail);
            detail = new Detail();
            details = detailService.showAll();
            return "index";
        }
        return null;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails() {
        this.details = details;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }
}
