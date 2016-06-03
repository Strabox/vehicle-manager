package com.pt.pires.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pt.pires.persistence.UserRepository;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		com.pt.pires.domain.User user = userRepository.findOne(username);
		return buildUser(user, buildUserAuthority());
	}
	
	/* ======================== Private methods ====================== */

	private User buildUser(com.pt.pires.domain.User myUser,List<GrantedAuthority> authorities){
		return new User(myUser.getUsername(), myUser.getPassword(), true,
				true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildUserAuthority() {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		setAuths.add(new SimpleGrantedAuthority("USER"));

		return new ArrayList<GrantedAuthority>(setAuths);
	}
	
}
