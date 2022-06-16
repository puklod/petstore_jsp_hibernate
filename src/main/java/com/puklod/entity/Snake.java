package com.puklod.entity;

import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "snake")
public class Snake extends Reptile{
	
	@Column(name="venomous", columnDefinition = "BOOLEAN")
	private boolean isVenomous;

	public boolean getIsVenomous() {
		return isVenomous;
	}

	public void setIsVenomous(boolean isVenomous) {
		this.isVenomous = isVenomous;
	}
	
	public void setIsVenomous(String isVenomous) {
		if(isVenomous != null) {
			this.isVenomous = Boolean.parseBoolean(isVenomous);
		}
	}
	
	public void validate() throws RequiredFieldEmptyException {
		if (this.getType().equals("") || this.getType() == null) {
			throw new RequiredFieldEmptyException("Egy kötelező mező nincs kitöltve");
		}
	}
}
