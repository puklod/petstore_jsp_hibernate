package com.puklod.entity;

import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "user_name", unique = true)
	@NotNull
	private String userName;
	@Column(name = "password")
	@NotNull
	private String password;
	@Column(name ="privileg")
	@NotNull
	private String privileg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if(id != null & !id.equals("")) {
			this.id = Integer.parseInt(id);
		}
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPrivileg() {
		return privileg;
	}
	public void setPrivileg(String privileg) {
		this.privileg = privileg;
	}
	public void validate() throws RequiredFieldEmptyException {
		if (this.userName.equals("") || this.password == null ||
			this.password.equals("") || this.password == null) {
			throw new RequiredFieldEmptyException("Egy kötelező mező nincs kitöltve!");
		}
	}
}
