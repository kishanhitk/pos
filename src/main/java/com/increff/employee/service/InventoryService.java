package com.increff.employee.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryDao dao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(InventoryPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    // Reduce inventory quantity
    @Transactional(rollbackFor = ApiException.class)
    public void reduce(String barcode, int id, int quantity) throws ApiException {
        InventoryPojo ex = getCheck(id);
        if (ex.getQuantity() < quantity) {
            throw new ApiException("Quantity not available for product, barcode:" + barcode);
        }
        ex.setQuantity(ex.getQuantity() - quantity);
        dao.update(ex);
    }

    public InventoryPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(rollbackFor = ApiException.class)
    public InventoryPojo update(Integer id, InventoryPojo p) throws ApiException {
        InventoryPojo ex = getCheck(id);
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
        return ex;
    }

    @Transactional(rollbackFor = ApiException.class)
    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo p = dao.selectByProductId(id);
        if (p == null) {
            throw new ApiException("Inventory with given id not found");
        }
        return p;
    }

    public void increase(Integer productId, Integer quantity) {
        InventoryPojo p = dao.selectByProductId(productId);
        p.setQuantity(p.getQuantity() + quantity);
        dao.update(p);
    }

    public InventoryPojo getByProductId(int productId) {
        return dao.selectByProductId(productId);
    }

}
