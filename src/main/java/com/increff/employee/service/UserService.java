package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.UserDao;
import com.increff.employee.pojo.UserPojo;

@Service
public class UserService {

	@Autowired
	private UserDao dao;

	@Transactional
	public void add(UserPojo p) {
		dao.insert(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public boolean verify(String email, String password) throws ApiException {
		UserPojo p = dao.select(email);
		return p!=null && (p.getPassword().equals(password));
	}

	@Transactional
	public List<UserPojo> getAll() {
		return dao.selectAll();
	}
}
