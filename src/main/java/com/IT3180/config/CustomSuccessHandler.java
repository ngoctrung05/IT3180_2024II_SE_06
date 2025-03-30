package com.IT3180.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.IT3180.util.TbConstants;

import java.io.IOException;
import java.util.Collection;
 
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess (HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
	{
		Collection <? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		for (GrantedAuthority authority : authorities)
		{
			String role = authority.getAuthority();
			
			if (role.equals(TbConstants.Role.ADMIN))
			{
				response.sendRedirect("/admin");
				return;
			}
			else if (role.equals(TbConstants.Role.USER))
			{
				response.sendRedirect("/user");
				return;
			}
			else if (role.equals(TbConstants.Role.GUEST))
			{
				response.sendRedirect("/guest");
				return;
			}
		}
		response.sendRedirect("/"); 
	}
}
