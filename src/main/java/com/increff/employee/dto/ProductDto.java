package com.increff.employee.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.ConvertUtil;
import com.increff.employee.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProductDto {
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackFor = ApiException.class)
    public ProductPojo add(ProductForm form) throws ApiException {
        validateForm(form);
        // Check if brandCategoryId isvalid
        brandCategoryService.get(form.getBrandCategory());
        ProductPojo product = ConvertUtil.convertProductFormToProductPojo(form);
        productService.add(product);
        // Initialize inventory
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(product.getId());
        inventoryPojo.setQuantity(0);
        inventoryService.add(inventoryPojo);
        return product;
    }

    @Transactional(readOnly = true)
    public List<ProductData> getAll() {
        List<ProductPojo> productPojos = productService.getAll();
        return productPojos.stream().map(ConvertUtil::convertProductPojoToProductData).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductData get(Integer id) throws ApiException {
        return ConvertUtil.convertProductPojoToProductData(productService.getCheck(id));
    }

    @Transactional(rollbackFor = ApiException.class)
    public ProductPojo update(Integer id, ProductForm form) throws ApiException {
        validateForm(form);
        ProductPojo productPojo = ConvertUtil.convertProductFormToProductPojo(form);
        return productService.update(id, productPojo);
    }

    public void validateForm(ProductForm f) throws ApiException {
        if (StringUtil.isEmpty(f.getName())) {
            throw new ApiException("Name cannot be empty.");
        }
        if (f.getMrp() <= 0) {
            throw new ApiException("MRP cannot be less than or equal to zero.");
        }
    }
}
