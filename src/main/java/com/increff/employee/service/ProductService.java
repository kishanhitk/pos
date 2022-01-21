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
        inventory.setId(p.getId());
        inventory.setQuantity(0);
        inventoryService.add(inventory);
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
        BrandCategoryPojo brandCategory = brandCategoryService.get(id);
        if (brandCategory == null) {
            throw new ApiException("BrandCategory not found");
        }
        return productDao.getProductByBrandCategory(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return productDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);
        if (p.getName() != null) {
            ex.setName(p.getName());
        }
        if (p.getBrandCategory() != null) {
            ex.setBrandCategory(p.getBrandCategory());
        }
        if (p.getMrp() != null) {
            ex.setMrp(p.getMrp());
        }
        productDao.update(ex);
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
