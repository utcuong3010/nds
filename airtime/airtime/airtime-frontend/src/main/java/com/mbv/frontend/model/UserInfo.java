package com.mbv.frontend.model;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

public class UserInfo implements UserDetails{

	GrantedAuthority[] authorities=null;
	public GrantedAuthority[] getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	public void setAuthorities(GrantedAuthority[] authorities ) {
		this.authorities=authorities;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return "test";
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return "rod";
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
