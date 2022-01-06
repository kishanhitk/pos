package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.BrandCategoryPojo;

@Repository
public class BrandCategoryDao extends AbstractDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandCategoryPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery("delete from BrandCategoryPojo p where id=:id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public BrandCategoryPojo select(int id) {
        TypedQuery<BrandCategoryPojo> query = getQuery("select p from BrandCategoryPojo p where id=:id",
                BrandCategoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BrandCategoryPojo> selectAll() {
        TypedQuery<BrandCategoryPojo> query = getQuery("select p from BrandCategoryPojo p", BrandCategoryPojo.class);
        return query.getResultList();
    }

    public void update(BrandCategoryPojo p) {
    }

}
