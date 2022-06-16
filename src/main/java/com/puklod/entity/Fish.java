package com.puklod.entity;

import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "fish")
public class Fish extends Aquatic {
	
	public void validate() throws RequiredFieldEmptyException {
		if (this.getType().equals("") || this.getType() == null) {
			throw new RequiredFieldEmptyException("Egy kötelező mező nincs kitöltve");
		}
	}

}
