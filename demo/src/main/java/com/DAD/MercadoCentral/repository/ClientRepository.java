package com.DAD.MercadoCentral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DAD.MercadoCentral.models.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, Long>{

	ClientModel findByNickname(String nickname);
	
	
	// crear
	
	// encontrar por id/ nombre/nickname/email
	
	// eliminar por id/ nombre y nickname/email
	
	// asignar /nombre/nickname/password/email
	
	// modificar /nombre/nickname/password/email

}
