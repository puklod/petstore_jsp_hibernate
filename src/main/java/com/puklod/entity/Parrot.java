package com.puklod.entity;

import com.puklod.exception.RequiredFieldEmptyException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="parrot")
public class Parrot extends Bird{
	@Column(name="can_talk", columnDefinition = "BOOLEAN")
	private boolean canTalk;

	public boolean isCanTalk() {
		return canTalk;
	}

	public void setCanTalk(boolean canTalk) {
		this.canTalk = canTalk;
	}
	
	public void setCanTalk(String canTalk) {
		if(canTalk != null) {
			this.canTalk = Boolean.parseBoolean(canTalk);
		}
	}
	
	public void validate() throws RequiredFieldEmptyException {
		if (this.getType().equals("") || this.getType() == null) {
			throw new RequiredFieldEmptyException("Egy kötelező mező nincs kitöltve!");
		}
	}

}
