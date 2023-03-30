package com.DAD.MercadoCentral.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAD.MercadoCentral.models.ClientModel;
import com.DAD.MercadoCentral.models.WorkerModel;
import com.DAD.MercadoCentral.repository.WorkerRepository;

// Clase que define los servicios que usan los controladores sobre los repostorios

@Service
public class WorkerService {
	
	@Autowired
	WorkerRepository wr;
	
	public Boolean isRegistredNickname(String nickname) {
		WorkerModel aux = wr.findByNickname(nickname);
		return aux != null;
	}
	
	public Boolean correctPassword(String nickname, String password) {
		WorkerModel aux = wr.findByNickname(nickname);
		return aux != null && aux.getPassword().matches(password);
	}

	public WorkerModel getWorkerByNickname(String nickname) {
		return wr.findByNickname(nickname);
	}

	public List<WorkerModel> getAllWorkers() {
		return wr.findAll();
	}

	public WorkerModel getWorkerById(long id) {
		
		return wr.findById(id).get();
	}

	public void modifiyWorker(long id, String nickname, String name, Boolean admin, String password) {

		WorkerModel w = wr.findById(id).get();
		w.setName(name);
		w.setNickname(nickname);
		w.setPassword(password);
		w.setIsManager(admin);
		wr.save(w);
		
	}

	public void addWorker(String nickname, String nombre, Boolean admin, String password) {
		WorkerModel w = new WorkerModel();
		w.setName(nombre);
		w.setNickname(nickname);
		w.setPassword(password);
		w.setIsManager(admin);
		wr.save(w);
		
	}

	public void deleteWorkerById(long id) {
		wr.deleteById(id);		
	}


}
