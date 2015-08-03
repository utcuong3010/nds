package com.mbv.frontend.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException, DataAccessException {
			UserInfo info=null;
			
			if (arg0.equalsIgnoreCase("rob"))
			{
//				 checking here to get password
				info=new UserInfo();
				info.setAuthorities(getAuthorities());
				throw new UsernameNotFoundException("khong tin thay user");
			}
			
		return info;
	}
	private GrantedAuthority[] getAuthorities() {
		GrantedAuthority[] auths = null;
		
		
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
//		get all permission from database by username
		authList.add(new GrantedAuthorityImpl("admin"));
		return authList.toArray(new GrantedAuthority[] {});
	}
}