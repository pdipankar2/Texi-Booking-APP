package com.jtc.india.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtc.india.binding.AuthRequest;
import com.jtc.india.entity.UserEntity;
import com.jtc.india.service.JwtService;
import com.jtc.india.service.MyUserDetailsService;

@RestController
@RequestMapping("/api")
public class UserRestController {
	
	@Autowired
	private JwtService jwtService;
	

	@Autowired
	private MyUserDetailsService service;
	
	@Autowired
	private PasswordEncoder peEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	//register
	
	@PostMapping("/register")
	public String registerUser(@RequestBody  UserEntity user) {
		
		
		String encode = peEncoder.encode(user.getPwd());
		user.setPwd(encode);
		
		boolean saveUser = service.saveUser(user);
		if(saveUser) {
			return "User Register  Successfully";
		}else {
		
		return "Registation Failed";
		}
	}
	
	
	//login
	
	@PostMapping("/login")
	public String userAuthentication(@RequestBody  AuthRequest request) {
		
		
		UsernamePasswordAuthenticationToken token=
				new UsernamePasswordAuthenticationToken(request.getUname(), request.getPwd());
		
		
		
		try {
		Authentication auth = authenticationManager.authenticate(token);
		
		if(auth.isAuthenticated()) {
			//generate jwt token
			
			return 	 jwtService.generateToken(request.getUname());
			
			
			
		}
		
		}catch(Exception e) {
		e.printStackTrace();
		}
		
			return "invalid Authentication";
		}
	
	

	//welcome
	
	
	@GetMapping("/welcome")
	public String welcomeMsg() {
		return "Welcome to india";
		
		
	}
	 	
	
	@GetMapping("/greet")
	public String greetMsg() {
		return "Good Morning to india";
		
		
	}
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

