package com.youssef.testo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {

		// User Role
		UserDetails user1 = User.withUsername("user1")
				.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode).password("user1")
				.roles("USER").build();

		UserDetails user2 = User.withUsername("user2")
				.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode).password("user2")
				.roles("USER").build();

		// Admin Role
		UserDetails theAdmin = User.withUsername("admin")
				.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode).password("admin")
				.roles("ADMIN").build();

		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

		userDetailsManager.createUser(user1);
		userDetailsManager.createUser(user2);
		userDetailsManager.createUser(theAdmin);

		return userDetailsManager;
	}
}