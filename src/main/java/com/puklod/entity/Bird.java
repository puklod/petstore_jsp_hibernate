package com.puklod.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Bird extends Animal {
	@Column(name="feather_color")
	private String featherColor;
	@Column(name="cage_size_requirment")
	private String cageSizeRequirment;
	public String getFeatherColor() {
		return featherColor;
	}
	public void setFeatherColor(String featherColor) {
		this.featherColor = featherColor;
	}
	public String getCageSizeRequirment() {
		return cageSizeRequirment;
	}
	public void setCageSizeRequirment(String cageSizeRequirment) {
		this.cageSizeRequirment = cageSizeRequirment;
	}
}
