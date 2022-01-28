package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public ProductPojo insert(ProductPojo p) {
        em.persist(p);
        return p;
    }

    @Transactional
    public int delete(int id) {
        Query query = em.createQuery("delete from ProductPojo p where id=:id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Transactional
    public ProductPojo getProductByBarcode(String barcode) {
        TypedQuery<ProductPojo> query = em.createQuery("select p from ProductPojo p where barcode=:barcode",
                ProductPojo.class);
        query.setParameter("barcode", barcode);
        List<ProductPojo> list = query.getResultList();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    // Get product by brand Category id
    @Transactional()
    public List<ProductPojo> getProductByBrandCategory(Integer id) {
        TypedQuery<ProductPojo> query = em.createQuery("select p from ProductPojo p where brandCategoryId=:id",
                ProductPojo.class);
        query.setParameter("id", id);
        List<ProductPojo> list = query.getResultList();
        return list;
    }

    public ProductPojo select(Integer id) {
        TypedQuery<ProductPojo> query = getQuery("select p from ProductPojo p where id=:id",
                ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery("select p from ProductPojo p order by p.name", ProductPojo.class);
        return query.getResultList();
    }

    @Transactional
    public ProductPojo update(ProductPojo p) {
        return em.merge(p);
    }
}
