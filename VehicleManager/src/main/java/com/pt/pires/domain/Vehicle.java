package com.pt.pires.domain;

/**
 * Abstract class Vehicle 
 * @author André
 *
 */
public abstract class Vehicle {

	private String name;
	
	private String brand;
	
	public Vehicle(String name,String brand){
		this.name = name;
		this.brand = brand;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getBrand(){
		return this.brand;
	}
	
}
