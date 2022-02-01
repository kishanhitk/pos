package com.increff.employee.dto;

import static org.junit.Assert.assertEquals;

import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.spring.AbstractUnitTest;
import com.increff.employee.util.TestUtil;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InventoryDtoTest extends AbstractUnitTest {

    @Autowired
    private InventoryDto inventoryDto;
    @Autowired
    private BrandCategoryDto brandDto;
    @Autowired
    private ProductDto productDto;

    @Test
    public void testInventoryInit() throws ApiException {
        BrandCategoryForm brandForm = TestUtil.getBrandForm("     AmUl        ", "dairY");
        BrandCategoryPojo branCategoryPojo = brandDto.addBrandCategory(brandForm);
        ProductForm productForm = TestUtil.getProductFormDto("nestle", branCategoryPojo.getId(), 10.50);
        ProductPojo productPojo = productDto.add(productForm);
        InventoryData inventoryData = inventoryDto.get(productPojo.getId());
        assertEquals(inventoryData.getQuantity(), 0);
    }

    @Test
    public void testAddInventory() throws ApiException {
        BrandCategoryForm brandForm = TestUtil.getBrandForm("     AmUl        ", "dairY");
        BrandCategoryPojo branCategoryPojo = brandDto.addBrandCategory(brandForm);
        ProductForm productForm = TestUtil.getProductFormDto("nestle", branCategoryPojo.getId(), 10.50);
        ProductPojo productPojo = productDto.add(productForm);
        InventoryForm inventoryForm = TestUtil.getInventoryForm(productPojo.getId(), 10);
        InventoryPojo inventoryPojo = inventoryDto.addInventory(inventoryForm);
        assertEquals(inventoryPojo.getQuantity(), 10, 0);
    }

    @Test
    public void testUpdateInventory() throws ApiException {
        BrandCategoryForm brandForm = TestUtil.getBrandForm("     AmUl        ", "dairY");
        BrandCategoryPojo branCategoryPojo = brandDto.addBrandCategory(brandForm);
        ProductForm productForm = TestUtil.getProductFormDto("nestle", branCategoryPojo.getId(), 10.50);
        ProductPojo productPojo = productDto.add(productForm);
        InventoryForm inventoryForm = TestUtil.getInventoryForm(productPojo.getId(), 10);
        InventoryPojo inventoryPojo = inventoryDto.update(productPojo.getId(), inventoryForm);
        assertEquals(inventoryPojo.getQuantity(), 10, 0);
    }

}
