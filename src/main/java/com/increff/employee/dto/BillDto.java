package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.BillData;
import com.increff.employee.model.InvoiceData;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BillDto {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional(rollbackFor = ApiException.class)
    public BillData getBillData(int id) throws ApiException {
        OrderPojo orderPojo = orderService.getById(id);
        List<OrderItemPojo> orderItemPojos = orderItemService.get(id);
        BillData billData = new BillData();
        // Change order status to confirmed
        orderPojo.setIsConfirmed(true);
        orderService.update(orderPojo);

        List<InvoiceData> bill = new ArrayList<InvoiceData>();
        int i = 1;
        // Convert OrderItemPojo to BillData
        for (OrderItemPojo o : orderItemPojos) {
            ProductPojo p = productService.get(o.getProductId());
            InvoiceData item = new InvoiceData();
            item.setId(i);
            item.setName(p.getName());
            item.setQuantity(o.getQuantity());
            item.setMrp(o.getSellingPrice());
            i++;
            bill.add(item);
        }
        billData.setItems(bill);
        billData.setId(orderPojo.getId());
        return billData;
    }
}
