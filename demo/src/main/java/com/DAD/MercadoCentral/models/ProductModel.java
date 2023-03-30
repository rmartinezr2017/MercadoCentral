package com.DAD.MercadoCentral.models;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Blob;

// Cada producto será almacenado aquí, si hay varias unidades, habra varias entradas.

@Entity
public class ProductModel {
	
	@Id
	private long id;
	
	private String name;
	private int price;
	
	@Lob
	@JsonIgnore
	private Blob image;
	
	@OneToOne(cascade = CascadeType.ALL)
	private BarcodeModel barcode;
	
	private int quant = 0;
	
	public ProductModel() {}
	
	public ProductModel(long id, String name, int price) {
		
		this.id = id;
		this.name = name;
		this.price = price;
		
	}
	
	public String getName() {
		return name;
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
	public long getBarcode() {
		if (barcode==null)
			return -1;
		return barcode.getBarcodeId();
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
		return "ProductModel [id=" + id + ", name=" + name + ", price=" + price + ", barcode=" + barcode.getBarcodeId() + "]";
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob blob) {
		this.image = blob;
	}
	
	


}
