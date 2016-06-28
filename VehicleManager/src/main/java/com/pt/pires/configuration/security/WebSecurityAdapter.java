package com.pt.pires.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.pt.pires.security.SecurityUtil;

/**
 * Class where all access/authorization is controlled.
 * @author André
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

	private final static int MAXIMUM_SESSIONS = 5;
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(SecurityUtil.passwordEncoder());
    }	
	
	/**
	 * Method where all the <b>authorization/access</b> logic is written
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/license").permitAll()
				.antMatchers("/home","/vehicles","/vehicle/**").access("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
				.and()
					.formLogin()
						.failureUrl("/login?error")
						.loginPage("/login")
						.defaultSuccessUrl("/home",true)
						.usernameParameter("username")
						.passwordParameter("password")
				.and()
					.logout()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.clearAuthentication(true)
						.invalidateHttpSession(true)
				.and()
					.csrf()
				.and()
					.exceptionHandling().accessDeniedPage("/accessDenied");
		http
			.sessionManagement()
			.maximumSessions(MAXIMUM_SESSIONS)
			.expiredUrl("/expiredSession");
	}

}
