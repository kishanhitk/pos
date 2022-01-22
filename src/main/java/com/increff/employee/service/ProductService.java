package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo add(ProductPojo p) throws ApiException {
        normalize(p);
        return productDao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(Integer id) {
        return productDao.select(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo getProductByBarcode(String barcode) throws ApiException {
        return productDao.getProductByBarcode(barcode);
    }

    // Get product by brand Category id
    @Transactional(rollbackOn = ApiException.class)
    public List<ProductPojo> getProductByBrandCategory(Integer id) throws ApiException {
        return productDao.getProductByBrandCategory(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo productPojo = getCheck(id);
        productPojo.setBrandCategoryId(p.getBrandCategoryId());
        productPojo.setName(p.getName());
        productPojo.setMrp(p.getMrp());
        return productDao.update(productPojo);
    }

    @Transactional
    public ProductPojo getCheck(Integer id) throws ApiException {
        ProductPojo p = productDao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }

    private void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()).trim());
    }
}
