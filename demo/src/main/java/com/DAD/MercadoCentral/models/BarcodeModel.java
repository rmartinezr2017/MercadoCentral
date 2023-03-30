package com.DAD.MercadoCentral.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class BarcodeModel {
	
	@Id
	private long barcodeId; 
	
	@OneToOne(mappedBy="barcode")
	private ProductModel product;
	
	protected BarcodeModel() {}
	
	public BarcodeModel (long id) {
		
		this.barcodeId = id;
		product = null;
		
	}

	public long getBarcodeId() {
		return barcodeId;
	}

	public void setBarcodeId(long barcodeId) {
		this.barcodeId = barcodeId;
	}

	public ProductModel getProduct() {
		return product;
	}

	public void setProduct(ProductModel product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "BarcodeModel [barcodeId=" + barcodeId + ", product=" + product.getName() + "]";
	}
	
	

}
