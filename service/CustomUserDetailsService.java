package product.info.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import product.info.model.User;



@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.findByUserName(username);
		
		if (user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}

		System.out.println("user : " + user.getFirstName());
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getStatus().equalsIgnoreCase("Active"), true, true, true,
				getGrantedAuthorities(user));

	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserProfiles().get(0).getType()));
		
		System.out.print("authorities :" + authorities);

		return authorities;
	}

}
