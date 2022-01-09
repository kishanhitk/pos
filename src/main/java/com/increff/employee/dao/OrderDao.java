package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.OrderPojo;

import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends AbstractDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p) {
        em.persist(p);
    }

    public OrderPojo select(int id) {
        return em.find(OrderPojo.class, id);
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery("select p from OrderPojo p", OrderPojo.class);
        return query.getResultList();
    }

    public void update(OrderPojo p) {
        em.merge(p);
    }

}
