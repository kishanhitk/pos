package com.increff.employee.service;

import java.util.List;

// import javax.transaction.Transactional;

import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// TODO: Replace Transactional from Spring | O it on all services

@Service
public class BrandCategoryService {

    @Autowired
    private BrandCategoryDao dao;

    @Transactional(rollbackFor = ApiException.class)
    // TODO: Rollback not needed here
    public void add(BrandCategoryPojo p) throws ApiException {
        normalize(p);
        // Throw exception if brand or category is empty
        if (StringUtil.isEmpty(p.getBrand()) || StringUtil.isEmpty(p.getCategory())) {
            throw new ApiException("Brand or Category cannot be empty");
        }
        checkByBrandCategoryName(p);
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackFor = ApiException.class)
    // TODO: Rollback not needed here
    public BrandCategoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(readOnly = true)
    public List<BrandCategoryPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
    public BrandCategoryPojo update(int id, BrandCategoryPojo p) throws ApiException {
        normalize(p);
        p.setId(id);
        return dao.update(p);
    }

    public void checkByBrandCategoryName(BrandCategoryPojo p) throws ApiException {
        BrandCategoryPojo ex = dao.getByBrandCategoryName(p.getBrand(), p.getCategory());
        if (ex != null) {
            throw new ApiException("Brand and Category already exists");
        }
    }

    @Transactional
    public BrandCategoryPojo getCheck(Integer id) throws ApiException {
        BrandCategoryPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("BrandCategory with given ID does not exit, id: " + id);
        }
        return p;
    }

    // TODO: change all normalize to protected
    private static void normalize(BrandCategoryPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()).trim());
        p.setCategory(StringUtil.toLowerCase(p.getCategory()).trim());
    }

    public List<BrandCategoryPojo> getByBrandName(String brand) {
        return dao.getByBrandName(brand);
    }
}
