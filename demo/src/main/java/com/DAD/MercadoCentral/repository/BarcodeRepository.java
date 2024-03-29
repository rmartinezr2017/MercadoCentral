package com.DAD.MercadoCentral.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DAD.MercadoCentral.models.BarcodeModel;

public interface BarcodeRepository extends JpaRepository<BarcodeModel, Long>{
	
	
	BarcodeModel findByBarcodeId(long id);

	List<BarcodeModel> findAllByProduct(Object object);

	void deleteByBarcodeId(long barcodeId);
	
	// crear codigo de barras
	
	// encontrar por id
	// encontrar por producto
	
	// asignar un producto a un codigo de barras
	// 
	
	// eliminar codigo de barras por id
	// eliminar codigo de barras por producto

	
}
