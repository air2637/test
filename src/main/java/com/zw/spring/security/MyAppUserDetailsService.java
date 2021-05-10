package com.zw.spring.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.zw.spring.repository.UserRepository;

@Component
public class MyAppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String userId)
			throws UsernameNotFoundException {
		
		com.zw.spring.domain.User login = userRepo.findById((long)Integer.valueOf(userId)).get();
		
		if (login==null) {
			throw new UsernameNotFoundException(String.format("User with the username %s does not exist", userId));
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		if(!login.getRole().isEmpty()) {
			authorities = AuthorityUtils.createAuthorityList(login.getRole());
		}
		
		UserDetails ud = new User(userId, login.getPassword(), authorities);
		
		return ud;
		
	}
	
	
	
}

