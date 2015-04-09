package com.raghunadimpalli.common.core.override.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.raghunadimpalli.common.core.helpers.MVCComponentHelper;

public class UrlParameterAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter{
	
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request){
		/*if(request.getParameterMap().size() == 2){
			return true;
		}
		return false;*/
		return true;
	}
	
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request){
		String[] credentials = new String[2];
		/*credentials[0] = request.getParameter("param1");
		credentials[1] = request.getParameter("param2");
		return credentials;*/
		credentials[0] = MVCComponentHelper.getUserRole("nadimpra");
		return credentials;
	}

}
