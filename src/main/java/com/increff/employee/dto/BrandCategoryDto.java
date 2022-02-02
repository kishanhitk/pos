package com.increff.employee.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.util.ConvertUtil;
import com.increff.employee.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BrandCategoryDto {

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Transactional(rollbackFor = ApiException.class)
    public BrandCategoryPojo addBrandCategory(BrandCategoryForm form) throws ApiException {
        validateData(form);
        BrandCategoryPojo brandPojo = ConvertUtil.convertBrandCategoryFormtoBrandCategoryPojo(form);
        return brandCategoryService.add(brandPojo);
    }

    @Transactional(readOnly = true)
    public BrandCategoryData getBrandCategoryById(int id) throws ApiException {
        BrandCategoryPojo brandPojo = brandCategoryService.get(id);
        return ConvertUtil.convertBrandCategoryPojotoBrandCategoryData(brandPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public BrandCategoryPojo updateBrandCategory(int id, BrandCategoryForm form) throws ApiException {
        validateData(form);
        BrandCategoryPojo brandPojo = ConvertUtil.convertBrandCategoryFormtoBrandCategoryPojo(form);
        return brandCategoryService.update(id, brandPojo);
    }

    @Transactional(readOnly = true)
    public List<BrandCategoryData> getAllBrandCategories() {
        List<BrandCategoryPojo> list = brandCategoryService.getAll();
        return list.stream().map(o -> ConvertUtil.convertBrandCategoryPojotoBrandCategoryData(o))
                .collect(Collectors.toList());
    }

    public void validateData(BrandCategoryForm f) throws ApiException {
        if (StringUtil.isEmpty(f.getBrand()) || StringUtil.isEmpty(f.getCategory())) {
            throw new ApiException("Brand or Category should NOT be empty.");
        }
    }
}
