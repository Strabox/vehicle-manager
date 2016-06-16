package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.VehicleUnlicensed;

public interface UnlicensedVehicleRepository extends CrudRepository<VehicleUnlicensed,String>  {

}
