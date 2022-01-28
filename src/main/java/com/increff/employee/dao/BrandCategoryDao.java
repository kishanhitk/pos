package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

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

    public BrandCategoryPojo getByBrandCategoryName(String Brand, String Name) {
        TypedQuery<BrandCategoryPojo> query = em.createQuery(
                "select p from BrandCategoryPojo p where p.brand=:brand and p.category=:category ",
                BrandCategoryPojo.class);
        query.setParameter("brand", Brand);
        query.setParameter("category", Name);
        List<BrandCategoryPojo> list = query.getResultList();
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public BrandCategoryPojo select(int id) {
        TypedQuery<BrandCategoryPojo> query = getQuery("select p from BrandCategoryPojo p where id=:id ",
                BrandCategoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BrandCategoryPojo> selectAll() {
        TypedQuery<BrandCategoryPojo> query = getQuery("select p from BrandCategoryPojo p order by p.brand, p.category",
                BrandCategoryPojo.class);
        return query.getResultList();
    }

    @Transactional
    public BrandCategoryPojo update(BrandCategoryPojo p) {
        // TODO: Use variable for query
        return em.merge(p);
    }

    public List<BrandCategoryPojo> getByBrandName(String brand) {
        TypedQuery<BrandCategoryPojo> query = em.createQuery(
                "select p from BrandCategoryPojo p where p.brand=:brand", BrandCategoryPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }
}
