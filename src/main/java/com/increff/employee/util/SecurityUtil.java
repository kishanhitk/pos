package com.increff.employee.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/*
https://stackoverflow.com/questions/4664893/how-to-manually-set-an-authenticated-user-in-spring-security-springmvc
*/
public class SecurityUtil {

	public static void setAuthentication(Authentication token) {
		SecurityContextHolder.getContext().setAuthentication(token);
	}

	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static UserPrincipal getPrincipal() {
		Authentication token = getAuthentication();
		return token == null ? null : (UserPrincipal) getAuthentication().getPrincipal();
	}

	public static void addAuthority(GrantedAuthority g) {
		Authentication old = getAuthentication();
		Collection<? extends GrantedAuthority> currentAuthorities = old.getAuthorities();
		if (currentAuthorities.contains(g)) {
			return;
		}
		List<GrantedAuthority> list = new ArrayList<>(currentAuthorities);
		list.add(g);
		UsernamePasswordAuthenticationToken fresh = //
				new UsernamePasswordAuthenticationToken(old.getPrincipal(), old.getCredentials(), list);
		fresh.setDetails(old.getDetails());
		setAuthentication(fresh);
	}

}
