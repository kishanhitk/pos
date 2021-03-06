package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.pojo.OrderItemPojo;

import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDao extends AbstractDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo newOrderItems) {
        em.persist(newOrderItems);
    }

    public OrderItemPojo select(int id) {
        return em.find(OrderItemPojo.class, id);
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery("select p from OrderItemPojo p", OrderItemPojo.class);
        return query.getResultList();
    }

    public List<OrderItemPojo> selectByOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery("select p from OrderItemPojo p where orderId=:orderId",
                OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Transactional
    public void update(OrderItemPojo p) {
        em.merge(p);
    }

    @Transactional
    public void deleteByOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery("select p from OrderItemPojo p where orderId=:orderId",
                OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        List<OrderItemPojo> list = query.getResultList();
        for (OrderItemPojo p : list) {
            em.remove(p);
        }
    }
}
