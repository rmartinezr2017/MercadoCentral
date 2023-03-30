package com.DAD.MercadoCentral.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DAD.MercadoCentral.models.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long>{

	List<ProductModel> findByIdBetween(long l, long m);
	
	// crear producto
	
	// eliminar producto por precio/nobre/id/codigo de barras
	
	
	// encontrar por codigo de barras
	// encontrar por id
	// encontrar por nombre
	// 
	
	// asignar codigo de barras
	// asignar precio
	
	// cambiar nombre
	// cambiar codigo de barras
	// cambiar precio
	

}
