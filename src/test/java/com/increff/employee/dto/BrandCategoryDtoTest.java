package com.increff.employee.dto;

import static org.junit.Assert.assertEquals;

import java.util.List;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.spring.AbstractUnitTest;
import com.increff.employee.util.TestUtil;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandCategoryDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandCategoryDto brandDto;

    @Test
    public void testAddBrand() throws ApiException {
        BrandCategoryForm brandForm = TestUtil.getBrandForm("       AmUl", "DaIrY ");
        BrandCategoryPojo pojo = brandDto.addBrandCategory(brandForm);
        BrandCategoryData data = brandDto.getBrandCategoryById(pojo.getId());
        assertEquals("amul", data.getBrand());
        assertEquals("dairy", data.getCategory());
    }

    @Test
    public void testGetAll() throws ApiException {
        BrandCategoryForm brandForm = TestUtil.getBrandForm("       AmUl", "DaIrY ");
        brandDto.addBrandCategory(brandForm);
        List<BrandCategoryData> brandDatas = brandDto.getAllBrandCategories();
        assertEquals(1, brandDatas.size());
    }

}
