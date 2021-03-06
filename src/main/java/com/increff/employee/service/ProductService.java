package com.increff.employee.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackFor = ApiException.class)
    public ProductPojo add(ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo existing = productDao.getProductByNameBrandCategoryIdMrp(p.getName(), p.getBrandCategoryId(),
                p.getMrp());
        if (existing != null) {
            throw new ApiException("Product with given details already exists");
        }
        return productDao.insert(p);
    }

    @Transactional(rollbackFor = ApiException.class)
    public ProductPojo get(Integer id) {
        return productDao.select(id);
    }

    @Transactional(rollbackFor = ApiException.class)
    public ProductPojo getProductByBarcode(String barcode) throws ApiException {
        ProductPojo productPojo = productDao.getProductByBarcode(barcode);
        if (productPojo == null) {
            throw new ApiException("Product with barcode " + barcode + " does not exist");
        }
        return productPojo;
    }

    // Get product by brand Category id
    @Transactional(rollbackFor = ApiException.class)
    public List<ProductPojo> getProductByBrandCategory(Integer id) throws ApiException {
        return productDao.getProductByBrandCategory(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }

    @Transactional(rollbackFor = ApiException.class)
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

    protected void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()).trim());
    }
}
