package com.raghunadimpalli.common.core.override.spring;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class LinkForbiddenEntryPoint implements AuthenticationEntryPoint{
	
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException{
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//httpResponse.sendRedirect("logout.jsp");
		//httpResponse.sendRedirect("/rainstar2-webapp/authError.xhtml");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	}
}