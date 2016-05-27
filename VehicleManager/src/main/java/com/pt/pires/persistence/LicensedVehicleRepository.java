package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.LicensedVehicle;

public interface LicensedVehicleRepository extends CrudRepository<LicensedVehicle, String>{

}
