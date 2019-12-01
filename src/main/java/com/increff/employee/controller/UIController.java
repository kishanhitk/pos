package com.increff.employee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.model.InfoData;

@Controller
public class UIController {

	@Value("${app.baseUrl}")
	private String baseUrl;

	// WEBSITE PAGES
	@RequestMapping(value = "")
	public String index() {
		return "index.html";
	}

	@RequestMapping(value = "/ui/login")
	public ModelAndView login() {
		return mav("login.html");
	}

	@RequestMapping(value = "/ui/logout")
	public ModelAndView logout() {
		return mav("logout.html");
	}

	@RequestMapping(value = "/ui/pricing")
	public ModelAndView pricing() {
		return mav("pricing.html");
	}

	// POST LOGIN PAGES
	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/users")
	public ModelAndView users() {
		return mav("users.html");
	}

	private ModelAndView mav(String page) {
		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", new InfoData());
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}

}
