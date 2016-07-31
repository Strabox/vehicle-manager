package com.pt.pires.configuration.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.pt.pires.security.SecurityUtil;

/**
 * Class where all access/authorization is controlled.
 * @author André
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

	private final static int MAXIMUM_SESSIONS = 3;
	
	// 30 Days (2592000 sec) token validity
	private final static int TOKEN_VALIDITY_SECONDS = 2592000;
	
	@Inject
	@Named("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@Inject
    DataSource dataSource;
	
	@Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(SecurityUtil.passwordEncoder());
    }	
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
	
	/**
	 * Method where all the <b>authorization/access</b> logic is written
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/license").permitAll()
				.antMatchers("/home","/vehicles/**","/vehicle/**")
				.access("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
				.and()
					.formLogin()
						.failureUrl("/login?error")
						.loginPage("/login")
						.defaultSuccessUrl("/home",false)
						.usernameParameter("username")
						.passwordParameter("password")
				.and()
					.rememberMe()
					.rememberMeParameter("remember-me")
					.tokenRepository(persistentTokenRepository())
					.tokenValiditySeconds(TOKEN_VALIDITY_SECONDS)
				.and()
					.logout()
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.clearAuthentication(true)
						.invalidateHttpSession(true)
				.and()
					.csrf()
				.and()
					.exceptionHandling().accessDeniedPage("/accessDenied")
				.and()
					.sessionManagement()
					.maximumSessions(MAXIMUM_SESSIONS)
					.expiredUrl("/expiredSession");
	}

}
