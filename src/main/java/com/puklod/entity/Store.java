package com.puklod.entity;

import java.util.Objects;
import java.util.Set;

import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "store")
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	@NotNull
	private String name;
	@Column(name = "address")
	@NotNull
	private String address;
	@OneToMany(cascade= {CascadeType.MERGE,CascadeType.REFRESH}, mappedBy = "store")
	private Set<Animal> animals;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if(id != null && !id.equals("")) {
			this.id = Integer.parseInt(id);
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<Animal> getAnimals() {
		return animals;
	}
	public void validate() throws RequiredFieldEmptyException {
		if (this.name.equals("") || this.name == null ||
			this.address.equals("") || this.address == null) {
			throw new RequiredFieldEmptyException("Egy kötelező mező nincs kitöltve!");
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(address, id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Store other = (Store) obj;
		return Objects.equals(address, other.address) && id == other.id
				&& Objects.equals(name, other.name);
	}
	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
	
	
}
