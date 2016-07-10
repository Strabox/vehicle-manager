package com.pt.pires.controllers.dtoUI;

import java.util.Date;

public class VehicleLicensedDTO {
	
	private String brand;
	
	private Date acquisitionDate;
	
	private String license;
	
	private Date licenseDate;

	public VehicleLicensedDTO() { }


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

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Date getLicenseDate() {
		return licenseDate;
	}

	public void setLicenseDate(Date licenseDate) {
		this.licenseDate = licenseDate;
	}
	
}
