package com.puklod.entity;

import com.puklod.exception.MinimumValueException;
import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dog")
public class Dog extends Mammal {
	public void validate() throws MinimumValueException, RequiredFieldEmptyException {
		if(this.getAge() < 0.5 ) {
			throw new MinimumValueException("Az életkornak minimum 0,5-nek kell lennie!");
		} else if (this.getType().equals("") || this.getType() == null) {
			throw new RequiredFieldEmptyException("Egy kötelező mező nincs kitöltve!");
		}
	}
}
