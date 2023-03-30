package com.DAD.MercadoCentral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DAD.MercadoCentral.models.WorkerModel;

public interface WorkerRepository extends JpaRepository<WorkerModel, Long>{

	WorkerModel findByNickname(String nickname);
	
	// crear
	
	// eliminar por id/nickname
	
	// encontrar por id/nickname
	
	//modificar /nombre/nickname/password/is manager
	
	
}
