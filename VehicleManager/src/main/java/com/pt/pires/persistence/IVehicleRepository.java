package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.Vehicle;

public interface IVehicleRepository extends CrudRepository<Vehicle,String> {

}
