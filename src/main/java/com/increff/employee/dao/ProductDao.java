package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery("delete from ProductPojo p where id=:id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery("select p from ProductPojo p where id=:id",
                ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery("select p from ProductPojo p", ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {
    }

}
