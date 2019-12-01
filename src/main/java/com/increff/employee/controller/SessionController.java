package com.increff.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.LoginForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SessionController {

	@Autowired
	private UserService service;

	@ApiOperation(value = "Logs in a user")
	@RequestMapping(path = "/api/session/login", method = RequestMethod.POST)
	public void login(@RequestBody LoginForm f) throws ApiException {
		boolean authenticated = service.verify(f.getEmail(), f.getPassword());
		if(!authenticated) {
			throw new ApiException("Username or password is invalid");
		}

	}

	@ApiOperation(value = "Logs out a user")
	@RequestMapping(path = "/api/session/logout", method = RequestMethod.GET)
	public void logout() {
	}

}
