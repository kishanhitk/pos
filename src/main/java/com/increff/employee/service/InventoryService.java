package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo p) throws ApiException {
        if (p.getQuantity() < 0) {
            throw new ApiException("Quantity can not be less than 0.");
        }
        dao.insert(p);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    // Reduce inventory quantity
    @Transactional(rollbackOn = ApiException.class)
    public void reduce(int id, int quantity) throws ApiException {
        InventoryPojo ex = getCheck(id);
        if (ex.getQuantity() < quantity) {
            throw new ApiException("Quantity not available for product, id:" + id);
        }
        ex.setQuantity(ex.getQuantity() - quantity);
        dao.update(ex);
    }

    public InventoryPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, InventoryPojo p) throws ApiException {
        if (p.getQuantity() < 0) {
            throw new ApiException("Quantity can not be less than 0.");
        }
        InventoryPojo ex = getCheck(id);
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Inventory with given id not found");
        }
        return p;
    }

    public void increase(Integer productId, Integer quantity) {
        InventoryPojo p = dao.select(productId);
        p.setQuantity(p.getQuantity() + quantity);
        dao.update(p);
    }

}
