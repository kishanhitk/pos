package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        BrandCategoryPojo brandCategory = brandCategoryService.get(p.getBrandCategory());
        if (brandCategory == null) {
            throw new ApiException("BrandCategory not found");
        }
        normalize(p);
        // Set inventory quantity to 0
        productDao.insert(p);
        InventoryPojo inventory = new InventoryPojo();
        inventory.setProductId(p.getId());
        inventory.setQuantity(0);
        inventoryService.add(inventory);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }

    @Transactional
    public ProductPojo get(int id) {
        return productDao.select(id);
    }

    @Transactional
    public void update(ProductPojo p) {
        productDao.update(p);
    }

    @Transactional
    public void delete(int id) {
        productDao.delete(id);
    }

    private void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }
}
