package com.puklod.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Aquatic extends Animal{
	@Column(name="color")
	private String color;
	@Column(name="tank_size_requirment")
	private String tankSizeRequirment;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTankSizeRequirment() {
		return tankSizeRequirment;
	}
	public void setTankSizeRequirment(String tankSizeRequirment) {
		this.tankSizeRequirment = tankSizeRequirment;
	}
	
	
}
