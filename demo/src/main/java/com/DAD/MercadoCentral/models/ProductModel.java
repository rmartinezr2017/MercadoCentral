package com.DAD.MercadoCentral.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

// Cada producto será almacenado aquí, si hay varias unidades, habra varias entradas.

@Entity
public class ProductModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private int price;
	
	@OneToOne()
	private BarcodeModel barcode;
	
	private String expiringDate = "";
	
	protected ProductModel() {}
	
	public ProductModel(String name, int price) {
		
		this.name = name;
		this.price = price;
		
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}	

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	///*
	public BarcodeModel getBarcode() {
		return barcode;
	}

	public void setBarcode(BarcodeModel barcode) {
		this.barcode = barcode;
	}
	//*/

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "ProductModel [id=" + id + ", name=" + name + ", price=" + price + ", barcode="/* + barcode.getBarcodeId() //*/+ "]";
	}

	public String getUnits() {
		return expiringDate;
	}

	public void setUnits(String expiringDate) {
		this.expiringDate = expiringDate;
	}
	
	


}
