package com.DAD.MercadoCentral.Security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.DAD.MercadoCentral.service.UsersDetailsService;

@SuppressWarnings("deprecation")
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public UsersDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Public pages
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/newClient").permitAll();
		http.authorizeRequests().antMatchers("/logout").permitAll();

		// Private pages (all other pages)
		http.authorizeRequests().antMatchers("/buy").hasAnyRole("client");
		http.authorizeRequests().antMatchers("/payconfirmation").hasAnyRole("client");
		http.authorizeRequests().antMatchers("/confirmedpurchase").hasAnyRole("client");
		http.authorizeRequests().antMatchers("/modclient").hasAnyRole("client");

		http.authorizeRequests().antMatchers("/worker/").hasAnyRole("worker", "admin");

		http.authorizeRequests().antMatchers("/worker/adminbarcodes").hasAnyRole("admin");
		http.authorizeRequests().antMatchers("/worker/addbarcodes").hasAnyRole("admin");
		http.authorizeRequests().antMatchers("/worker/deletebarcode").hasAnyRole("admin");
		http.authorizeRequests().antMatchers("/worker/asignedproduct").hasAnyRole("admin");
		http.authorizeRequests().antMatchers("/worker/newworker").hasAnyRole("admin");
		http.authorizeRequests().antMatchers("/worker/addworker").hasAnyRole("admin");
		http.authorizeRequests().antMatchers("/worker/modworker").hasAnyRole("admin");

		// Login form
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("nickname");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/logged");
		http.formLogin().failureUrl("/login");

		// Logout
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/loginout");

	}

}
