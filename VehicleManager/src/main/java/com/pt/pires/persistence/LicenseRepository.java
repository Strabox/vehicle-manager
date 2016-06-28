package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.License;

public interface LicenseRepository extends CrudRepository<License, String> {

}
