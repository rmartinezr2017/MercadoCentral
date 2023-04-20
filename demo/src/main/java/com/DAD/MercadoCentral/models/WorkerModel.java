package com.DAD.MercadoCentral.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WorkerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	private String name;
	private String nickname;

	private String password;

	private Boolean isManager;

	public WorkerModel() {
	}

	public WorkerModel(String name, String nickname, String password, Boolean isManager) {

		this.name = name;
		this.nickname = nickname;
		this.password = password;
		this.setIsManager(isManager);

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

	public Boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	public long getId() {
		return Id;
	}

	@Override
	public String toString() {
		return "UserModel [Id=" + Id + ", name=" + name + ", nickname=" + nickname + ", password=" + password
				+ ", is manager=" + isManager + "]";
	}

}
