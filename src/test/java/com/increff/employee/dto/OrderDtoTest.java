package com.increff.employee.dto;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import com.google.protobuf.TextFormat.ParseException;
import com.increff.employee.model.BillData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.InvoiceData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.spring.AbstractUnitTest;
import com.increff.employee.util.TestUtil;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderDtoTest extends AbstractUnitTest {

    @Autowired
    private OrderDto orderDto;
    @Autowired
    private BrandCategoryDto brandDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private InventoryDto inventoryDto;

    @Test
    public void testCreateOrder() throws ApiException, ParseException {
        // get array of items
        OrderItemForm[] orderItemForms = getOrderItemArray();
        // create order
        BillData billData = orderDto.addOrder(Arrays.asList(orderItemForms));
        List<InvoiceData> billDatas = billData.getItems();
        // test values
        assertEquals(2, billDatas.size());
        assertEquals("bubbly", billDatas.get(0).name);
        assertEquals("peanut butter", billDatas.get(1).name);
        assertEquals(10, billDatas.get(0).mrp, 0.01);
        assertEquals(15, billDatas.get(1).mrp, 0.01);
    }

    private OrderItemForm[] getOrderItemArray() throws ApiException {
        ProductData productData1 = getProductData("nestle", "dairy", "bubbly", 10);
        InventoryForm inventoryForm1 = TestUtil.getInventoryForm(productData1.getId(), 20);
        inventoryDto.addInventory(inventoryForm1);
        ProductData productData2 = getProductData("nestle", "food", "peanut butter", 15);
        InventoryForm inventoryForm2 = TestUtil.getInventoryForm(productData2.getId(), 20);
        inventoryDto.addInventory(inventoryForm2);

        return TestUtil.getOrderItemFormArrayDto(productData1.getBarcode(), productData2.getBarcode(),
                4, 5, productData1.getMrp(), productData2.getMrp());
    }

    private ProductData getProductData(String brand, String category, String name, double mrp) throws ApiException {
        BrandCategoryForm brandForm = TestUtil.getBrandForm(brand, category);
        BrandCategoryPojo brandCategoryPojo = brandDto.addBrandCategory(brandForm);
        ProductForm productForm = TestUtil.getProductFormDto(name, brandCategoryPojo.getId(), mrp);
        ProductPojo productPojo = productDto.add(productForm);
        return productDto.get(productPojo.getId());
    }

}
