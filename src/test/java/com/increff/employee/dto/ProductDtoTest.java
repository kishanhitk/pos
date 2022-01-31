package com.increff.employee.dto;

import static org.junit.Assert.assertEquals;

import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.spring.AbstractUnitTest;
import com.increff.employee.util.TestUtil;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandCategoryDto brandDto;

    @Autowired
    private ProductDto productDto;

    @Test
    public void testAdd() throws ApiException {
        // add data
        BrandCategoryForm brandForm = TestUtil.getBrandForm("     AmUl        ", "dairY");
        BrandCategoryPojo branCategoryPojo = brandDto.addBrandCategory(brandForm);
        ProductForm productForm = TestUtil.getProductFormDto("nestle", branCategoryPojo.getId(), 10.50);
        ProductPojo productPojo = productDto.add(productForm);
        // Get data
        ProductData productData = productDto.get(productPojo.getId());
        // test
        assertEquals(productPojo.getId(), productData.getId());
        assertEquals(productPojo.getName(), productData.getName());
        assertEquals(productPojo.getBrandCategoryId(), productData.getBrandCategory(), 0);
        assertEquals(productPojo.getMrp(), productData.getMrp(), 0.0);
    }

}
