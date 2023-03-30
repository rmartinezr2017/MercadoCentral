package com.DAD.MercadoCentral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAD.MercadoCentral.models.ClientModel;
import com.DAD.MercadoCentral.repository.ClientRepository;


@Service
public class ClientService {
	
	@Autowired
	private ClientRepository cr;

	public Boolean isRegistredNickname(String nickname) {
		ClientModel aux = cr.findByNickname(nickname);
		return aux != null;
	}
	
	public Boolean correctPassword(String nickname, String password) {
		ClientModel aux = cr.findByNickname(nickname);
		return aux != null && aux.getPassword().matches(password);
	}

	public void addClient(String nickname, String nombre, String email, String direccion, String pago, String password) {
		
		ClientModel newClient = new ClientModel(nombre, nickname, password, email);
		newClient.setDirection(direccion);
		newClient.setCreditCard(pago);
		cr.save(newClient);
		
	}

	public ClientModel getClientByNickname(String nickname) {	
		return cr.findByNickname(nickname);
	}

	public ClientModel getById(long id) {
		return cr.findById(id).get();
	}

	public void modifiyClient(long id, String nickname, String nombre, String email, String direccion, String pago, String password) {
		
		ClientModel c = cr.findById(id).get();
		c.setName(nombre);
		c.setNickname(nickname);
		c.setEmail(email);
		c.setDirection(direccion);
		c.setCreditCard(pago);
		c.setPassword(password);
		cr.save(c);		
		
	}

}
