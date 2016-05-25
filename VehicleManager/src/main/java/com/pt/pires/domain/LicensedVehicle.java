package com.pt.pires.domain;

public class LicensedVehicle extends Vehicle{

	private License license;
	
	public LicensedVehicle(String name, String brand) {
		super(name, brand);
		setLicense(license);
	}
	
	public License getLicense(){
		return license;
	}
	
	public void setLicense(License license){
		this.license = license;
	}
	
}
