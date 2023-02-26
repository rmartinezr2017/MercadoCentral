package com.DAD.MercadoCentral.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

@Entity
public class ClientModel {
	
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;
	
	private String name;
	private String nickname;
	private String email;
	
	private String password;
	
	@OneToMany
	private List<ProductModel> shoppingCart;
	
	protected ClientModel() {}
	
	public ClientModel(String name, String nickname, String password, String email) {
		
		this.name = name;
		this.nickname = nickname;
		this.password = password;
		this.shoppingCart = new LinkedList<ProductModel>();
		this.email = email;
				
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ProductModel> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(List<ProductModel> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public long getId() {
		return Id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	

	@Override
	public String toString() {
		return "UserModel [Id=" + Id + ", name=" + name + ", nickname=" + nickname + ", password=" + password + ", email=" + email + "]";
	}

}
