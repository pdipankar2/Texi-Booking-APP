package com.jtc.india.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jtc.india.filter.AppFilter;
import com.jtc.india.service.MyUserDetailsService;

@Configuration
public class AppConfig {
	
	@Autowired
    private MyUserDetailsService userDetailsService;
	
	@Autowired
	private AppFilter filter;
	
	
	@Bean
	public PasswordEncoder pwEncoder() {
		return new BCryptPasswordEncoder();
		
		
	}
	
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authenticationProvider
		
		=new DaoAuthenticationProvider();
		
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(pwEncoder());
		
		return authenticationProvider;
		
	}
	
	
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
		
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		
		  return http.csrf().disable()
	                .authorizeHttpRequests()
	                .requestMatchers("/api/login","/api/register").permitAll()
	                .and()
	                .authorizeHttpRequests().requestMatchers("/api/**")
	                .authenticated()
	                .and()
	                .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .authenticationProvider(authProvider())
	                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();

	}
	
	
	
	
	
	
	
}
