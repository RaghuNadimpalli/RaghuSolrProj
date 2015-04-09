package com.raghunadimpalli.common.core.override.spring;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SuppressWarnings("deprecation")
public class UserDetailsServiceImpl implements AuthenticationUserDetailsService<Authentication> {
	
	@Override
	public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException{
		
		UserDetails userDetails = null;
		String[] credientials = new String[10];
		credientials = (String[]) token.getCredentials();
		boolean principal = Boolean.valueOf((Boolean) token.getPrincipal());
		
		if(credientials != null && principal == true){
			String name = credientials[0];
			if("admin".equalsIgnoreCase(name)){
				userDetails = getAdminUser(name);
			}
			else if("handler".equalsIgnoreCase(name)){
				userDetails = getRetailerUser(name);
			}
			else if("user".equalsIgnoreCase(name)){
				userDetails = getUserUser(name);
			}
		}
		
		if(userDetails == null){
			throw new UsernameNotFoundException("Could not load user:" + token.getName());
		}
		return userDetails;
	}
	
	private UserDetails getAdminUser(String username){
		Collection<GrantedAuthority> grantedAuthorites = new ArrayList<GrantedAuthority>();
		grantedAuthorites.add(new GrantedAuthorityImpl("ROLE_USER"));
		grantedAuthorites.add(new GrantedAuthorityImpl("ROLE_RETAILER"));
		grantedAuthorites.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		return new User(username, "notused", true, true,true,true,
				grantedAuthorites);
	}
	
	private UserDetails getRetailerUser(String username){
		Collection<GrantedAuthority> grantedAuthorites = new ArrayList<GrantedAuthority>();
		grantedAuthorites.add(new GrantedAuthorityImpl("ROLE_USER"));
		grantedAuthorites.add(new GrantedAuthorityImpl("ROLE_RETAILER"));
		return new User(username, "notused", true, true,true,true,
				grantedAuthorites);
	}
	
	private UserDetails getUserUser(String username){
		Collection<GrantedAuthority> grantedAuthorites = new ArrayList<GrantedAuthority>();
		grantedAuthorites.add(new GrantedAuthorityImpl("ROLE_USER"));
		return new User(username, "notused", true, true,true,true,
				grantedAuthorites);
	}
}
