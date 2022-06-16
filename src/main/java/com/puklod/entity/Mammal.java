package com.puklod.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Mammal extends Animal {
	@Column(name = "age")
	private double age;
	@Column(name = "fur_length")
	private String furLength;
	@Column(name = "fur_color")
	private String furColor;
	
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public void setAge(String age) {
		if(age.equals("") || age == null) {
			this.age = 0;
		} else {
			this.age = Double.parseDouble(age);
		}
	}
	public String getFurLength() {
		return furLength;
	}
	public void setFurLength(String furType) {
		this.furLength = furType;
	}
	public String getFurColor() {
		return furColor;
	}
	public void setFurColor(String furColor) {
		this.furColor = furColor;
	}
	
	
}
