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

        dao.insert(p);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    public InventoryPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, InventoryPojo p) throws ApiException {
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

}
