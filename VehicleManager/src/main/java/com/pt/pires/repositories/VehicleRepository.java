package com.pt.pires.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.Vehicle;


public interface VehicleRepository extends CrudRepository<Vehicle, String>{

	
}
