package com.mbv.security;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.mbv.services.xsecd.SessionInfo;

public class AtUserDetails implements UserDetails {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8710398493799609490L;
	
	SessionInfo info;
	GrantedAuthority[] authorities;
	
	public AtUserDetails(SessionInfo data, GrantedAuthority[] groups)
	{
		
		info = data;
		authorities = groups;
	}
	
	public SessionInfo getSessionInfo() {
		return info;
	}

	@Override
	public GrantedAuthority[] getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return info.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return info.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return !info.getUserInfo().isDisabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return info.getUserInfo().isActivated() && !info.getUserInfo().isDisabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return info.getUserInfo().isActivated() && !info.getUserInfo().isDisabled();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return !info.getUserInfo().isDisabled();
	}
}
