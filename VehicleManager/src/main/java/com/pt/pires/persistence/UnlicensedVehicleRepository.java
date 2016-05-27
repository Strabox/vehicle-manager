package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.UnlicensedVehicle;

public interface UnlicensedVehicleRepository extends CrudRepository<UnlicensedVehicle,String>  {

}
