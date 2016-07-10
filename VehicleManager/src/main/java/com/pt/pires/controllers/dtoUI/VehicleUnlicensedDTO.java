package com.pt.pires.controllers.dtoUI;

import java.util.Date;

public class VehicleUnlicensedDTO {
	
	private String brand;
	
	private Date acquisitionDate;
	
	private int fabricationYear;

	public VehicleUnlicensedDTO() { }

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public int getFabricationYear() {
		return fabricationYear;
	}

	public void setFabricationYear(int fabricationYear) {
		this.fabricationYear = fabricationYear;
	}
	
	
}
