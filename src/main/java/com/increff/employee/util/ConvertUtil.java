package com.increff.employee.util;

import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;

public class ConvertUtil {
    public OrderItemPojo convert(OrderItemForm form) {
        OrderItemPojo pojo = new OrderItemPojo();
        pojo.setQuantity(form.getQuantity());
        pojo.setSellingPrice(form.getSellingPrice());
        return pojo;
    }
}
