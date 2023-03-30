package com.DAD.MercadoCentral.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsService implements UserDetailsService{

	@Autowired
	ClientService cs;
	@Autowired
	WorkerService ws;
	
	@Override
	public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {		
		
		// Comprobar si estas en el sistema
		// si estas continuar loguear
		// si no, introducir datos y continuar
	
		Boolean isClient, isWorker;
		isClient = cs.isRegistredNickname(nickname);
		isWorker = ws.isRegistredNickname(nickname);
				
		if (isClient && !isWorker) { // Es un cliente			
						
			List<GrantedAuthority> role = new ArrayList<GrantedAuthority>();
			role.add(new SimpleGrantedAuthority("ROLE_" + "client"));
			return new org.springframework.security.core.userdetails.User(nickname, 
																		  cs.getClientByNickname(nickname).getPassword(), 
																		  role);
		
		} else if (!isClient && isWorker) { // Es un empleado			
			
			List<GrantedAuthority> role = new ArrayList<>();
			if (ws.getWorkerByNickname(nickname).getIsManager())
				role.add(new SimpleGrantedAuthority("ROLE_" + "admin"));
			else
				role.add(new SimpleGrantedAuthority("ROLE_" + "worker"));
			
			return new org.springframework.security.core.userdetails.User(nickname, 
																		  ws.getWorkerByNickname(nickname).getPassword(), 
																		  role);
		
		}
		
		System.out.println("fdsfsdsfdfsdfsdsfdfsdsdffsdfsdfsdfsd");
		throw (new UsernameNotFoundException("User not found"));
		
	}

}
