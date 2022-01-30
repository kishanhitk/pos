package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.InvoiceData;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.ConvertUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderDto {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackFor = ApiException.class)
    public List<InvoiceData> addOrder(List<OrderItemForm> orderItems) throws ApiException {
        validate(orderItems);
        OrderPojo orderPojo = orderService.createNewOrder();
        List<OrderItemPojo> orderItemPojos = new ArrayList<>();
        for (OrderItemForm orderItem : orderItems) {
            ProductPojo product = productService.getProductByBarcode(orderItem.getBarcode());
            if (product == null) {
                throw new ApiException("Product with barcode " + orderItem.getBarcode() + " not found");
            }
            OrderItemPojo orderItemPojo = ConvertUtil.convertOrderItemFormToOrderItemPojo(orderItem);
            orderItemPojo.setOrderID(orderPojo.getId());
            orderItemPojo.setProductId(product.getId());
            orderItemPojos.add(orderItemPojo);
            orderItemService.insert(orderItemPojo);
            inventoryService.reduce(orderItemPojo.getProductId(), orderItemPojo.getQuantity());
        }
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
        return bill;
    }

    @Transactional(readOnly = true)
    public List<OrderData> getAllOrders() throws ApiException {
        List<OrderPojo> orderPojos = orderService.getAll();
        List<OrderData> orderDatas = new ArrayList<OrderData>();
        for (OrderPojo orderPojo : orderPojos) {
            List<OrderItemPojo> orderItemPojos = orderItemService.get(orderPojo.getId());
            List<OrderItemData> orderItemDatas = new ArrayList<OrderItemData>();
            for (OrderItemPojo orderItemPojo : orderItemPojos) {
                ProductPojo product = productService.get(orderItemPojo.getProductId());
                orderItemDatas
                        .add(ConvertUtil.convertOrderItemPojoToOrderItemData(orderItemPojo, product.getBarcode()));
            }
            OrderData orderData = ConvertUtil.convertOrderPojoToOrderData(orderPojo, orderItemDatas);
            orderDatas.add(orderData);
        }
        return orderDatas;
    }

    @Transactional(readOnly = true)
    public OrderData getOrderDetails(int id) throws ApiException {
        OrderPojo orderPojo = orderService.getById(id);
        List<OrderItemPojo> orderItemPojos = orderItemService.get(orderPojo.getId());
        List<OrderItemData> orderItemDatas = new ArrayList<OrderItemData>();
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            ProductPojo product = productService.get(orderItemPojo.getProductId());
            orderItemDatas
                    .add(ConvertUtil.convertOrderItemPojoToOrderItemData(orderItemPojo, product.getBarcode()));
        }
        OrderData orderData = ConvertUtil.convertOrderPojoToOrderData(orderPojo, orderItemDatas);
        return orderData;
    }

    @Transactional(rollbackFor = ApiException.class)
    public List<InvoiceData> updateOrder(int orderId, List<OrderItemForm> orderItems) throws ApiException {
        validate(orderItems);
        revertInventory(orderId);
        List<OrderItemPojo> newOrderItems = new ArrayList<OrderItemPojo>();
        for (OrderItemForm orderItem : orderItems) {
            ProductPojo product = productService.getProductByBarcode(orderItem.getBarcode());
            if (product == null) {
                throw new ApiException("Product with barcode " + orderItem.getBarcode() + " not found");
            }
            OrderItemPojo orderItemPojo = ConvertUtil.convertOrderItemFormToOrderItemPojo(orderItem);
            orderItemPojo.setOrderID(orderId);
            orderItemPojo.setProductId(product.getId());
            newOrderItems.add(orderItemPojo);
            inventoryService.reduce(orderItemPojo.getProductId(), orderItemPojo.getQuantity());
        }
        orderItemService.deleteByOrderId(orderId);
        orderItemService.insertMutiple(newOrderItems);
        List<InvoiceData> bill = new ArrayList<InvoiceData>();
        int i = 1;
        // Convert OrderItemPojo to BillData
        for (OrderItemPojo o : newOrderItems) {
            ProductPojo p = productService.get(o.getProductId());
            InvoiceData item = new InvoiceData();
            item.setId(i);
            item.setName(p.getName());
            item.setQuantity(o.getQuantity());
            item.setMrp(o.getSellingPrice());
            i++;
            bill.add(item);
        }
        return bill;
    }

    @Transactional(rollbackFor = ApiException.class)
    public void revertInventory(int orderId) throws ApiException {
        List<OrderItemPojo> orderItemPojoList = orderItemService.selectByOrderId(orderId);
        for (OrderItemPojo orderItemPojo : orderItemPojoList) {
            inventoryService.increase(orderItemPojo.getProductId(), orderItemPojo.getQuantity());
        }
    }

    private void validate(List<OrderItemForm> orderItems) throws ApiException {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new ApiException("Order items cannot be empty");
        }
        for (OrderItemForm orderItem : orderItems) {
            if (orderItem.getQuantity() <= 0) {
                throw new ApiException("Quantity cannot be less than or equal to 0");
            }
        }
    }
}
