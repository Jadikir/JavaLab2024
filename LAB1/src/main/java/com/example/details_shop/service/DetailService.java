package com.example.details_shop.service;

import com.example.details_shop.entity.Detail;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
public class DetailService {

    @PersistenceContext(unitName = "detailsShop")
    private EntityManager entityManager;

    @Transactional
    public void create(Detail detail) {
        entityManager.persist(detail);
    }

    @Transactional
    public void update(Detail detail) {
        entityManager.merge(detail);
    }

    @Transactional
    public void delete(Long id) {
        Detail detail = entityManager.find(Detail.class, id);
        if (detail != null) {
            entityManager.remove(detail);
        }
    }

    public Detail show(Long id) {
        return entityManager.find(Detail.class, id);
    }

    public List<Detail> showAll() {
        TypedQuery<Detail> query = entityManager.createQuery("select d from Detail d", Detail.class);
        return query.getResultList();
    }
}
