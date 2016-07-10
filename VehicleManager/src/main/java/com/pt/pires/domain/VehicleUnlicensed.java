package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.util.DateUtil;

/**
 * Represents a vehicle that doesn't have a license plate
 * @author André
 *
 */
@Entity
public class VehicleUnlicensed extends Vehicle {

	@Column
	private int fabricationYear;

	
	public VehicleUnlicensed(String name, String brand,Date acquisitionDate,int fabricationYear) 
			throws InvalidVehicleNameException, InvalidVehicleBrandException {
		super(name, brand, acquisitionDate);
		setFabricationYear(fabricationYear);
	}
	
	public VehicleUnlicensed() { }	//Needed for JPA/JSON

	
	public int calculateAge() {
		Calendar currentCalendar = DateUtil.getCalendar(new Date());
		int currentYear = currentCalendar.get(Calendar.YEAR);
		return currentYear - fabricationYear;
	}
	
	/* === Getters and Setters === */
	
	public int getFabricationYear() {
		return fabricationYear;
	}

	public void setFabricationYear(int fabricationYear) {
		this.fabricationYear = fabricationYear;
	}
	
}
