package com.pt.pires.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Abstract class Vehicle 
 * @author André
 *
 */
@Entity
public abstract class Vehicle {

	@Id
	private String name;
	
	@Column
	private String brand;
	
	@Transient
	private List<Registration> registries = new ArrayList<>();
	
	public Vehicle(String name,String brand){
		setName(name);
		setBrand(brand);
	}
	
	public Vehicle() {}
	
	public void addRegistration(Registration reg){
		registries.add(reg);
	}
	
	/* === Getters and Setters === */
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getBrand(){
		return this.brand;
	}
	
	public void setBrand(String brand){
		this.brand = brand;
	}
	
}
