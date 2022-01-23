package com.increff.employee.service;

import java.util.List;

import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandCategoryService {

    @Autowired
    private BrandCategoryDao dao;

    @Transactional()
    // TODO: Rollback not needed here
    public BrandCategoryPojo add(BrandCategoryPojo p) throws ApiException {
        normalize(p);
        getCheckByBrandCategoryName(p.getBrand(), p.getCategory());
        dao.insert(p);
        return p;
    }

    @Transactional(readOnly = true)
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
        getCheckByBrandCategoryName(p.getBrand(), p.getCategory());
        BrandCategoryPojo brandCategoryPojo = getCheck(id);
        brandCategoryPojo.setCategory(p.getCategory());
        brandCategoryPojo.setBrand(p.getBrand());
        dao.update(brandCategoryPojo);
        return brandCategoryPojo;
    }

    public void getCheckByBrandCategoryName(String brandName, String categoryName) throws ApiException {
        BrandCategoryPojo ex = dao.getByBrandCategoryName(brandName, categoryName);
        if (ex != null) {
            throw new ApiException("Brand and Category combination already exists");
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
    protected static void normalize(BrandCategoryPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()).trim());
        p.setCategory(StringUtil.toLowerCase(p.getCategory()).trim());
    }

    public List<BrandCategoryPojo> getByBrandName(String brand) {
        return dao.getByBrandName(brand);
    }
}
