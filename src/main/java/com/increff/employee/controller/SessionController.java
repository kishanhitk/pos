package com.increff.employee.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.model.LoginForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;
import com.increff.employee.util.SecurityUtil;
import com.increff.employee.util.UserPrincipal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SessionController {

	@Autowired
	private UserService service;

	@ApiOperation(value = "Logs in a user")
	@RequestMapping(path = "/api/session/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, @RequestBody LoginForm f) throws ApiException {
		UserPojo p = service.get(f.getEmail());
		boolean authenticated = (p != null && (p.getPassword().equals(f.getPassword())));
		if (!authenticated) {
			throw new ApiException("Username or password is invalid");
		}

		// Create authentication object
		Authentication authentication = convert(p);
		// Create new session
		HttpSession session = request.getSession(true);
		// Attach Spring SecurityContext to this new session
		SecurityUtil.createContext(session);
		// Attach Authentication object to the Security Context
		SecurityUtil.setAuthentication(authentication);

		return new ModelAndView("redirect:/ui/login");

	}

	@ApiOperation(value = "Logs out a user")
	@RequestMapping(path = "/api/session/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/ui/logout");
	}

	private static Authentication convert(UserPojo p) {
		// Create principal
		UserPrincipal principal = new UserPrincipal();
		principal.setEmail(p.getEmail());
		principal.setId(p.getId());

		// Create Authorities
		ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(p.getRole()));
		// you can add more roles if rqrequired

		// Create Authentication
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null,
				authorities);
		return token;
	}

}
