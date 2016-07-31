package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.License;

public interface ILicenseRepository extends CrudRepository<License, String> {

}
