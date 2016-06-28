package com.pt.pires.services.local;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.UserRole;
import com.pt.pires.persistence.UserRepository;

/**
 * Service used to spring's authentication 
 * @author André
 *
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		if(username == null) {
			throw new IllegalArgumentException();
		}
		com.pt.pires.domain.User user = userRepository.findOne(username);
		if(user == null) {
			throw new UsernameNotFoundException("User doesn't exist");
		}
		return buildUser(user, buildUserAuthority(user.getRole()));
	}
	
	/* ======================== Private axiliary methods ====================== */

	private User buildUser(com.pt.pires.domain.User myUser,List<GrantedAuthority> authorities) {
		return new User(myUser.getUsername(), myUser.getPassword(), true,
				true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildUserAuthority(UserRole role) {
		ArrayList<GrantedAuthority> res = new ArrayList<>();
		res.add(new SimpleGrantedAuthority(role.name()));
		return new ArrayList<GrantedAuthority>(res);
	}
	
}
