package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional
    public ProductPojo get(int id) {
        return dao.select(id);
    }

    @Transactional
    public void update(ProductPojo p) {
        dao.update(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

}
