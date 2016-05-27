package com.pt.pires.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.persistence.LicensedVehicleRepository;
import com.pt.pires.persistence.UnlicensedVehicleRepository;
import com.pt.pires.persistence.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	private UnlicensedVehicleRepository unlicensedRepository;
	
	@Autowired
	private LicensedVehicleRepository licensedRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	
	public void removeVehicle(String vehicleName){
		vehicleRepository.delete(vehicleName);
	}
	
	public Collection<UnlicensedVehicle> getUnlicensedVehicles(){
		return (List<UnlicensedVehicle>) unlicensedRepository.findAll();
	}
	
	public Collection<LicensedVehicle> getLicensedVehicles(){
		return (List<LicensedVehicle>) licensedRepository.findAll();
	}
	
	public LicensedVehicle getLicensedVehicle(String name){
		return licensedRepository.findOne(name);
	}
	
}
