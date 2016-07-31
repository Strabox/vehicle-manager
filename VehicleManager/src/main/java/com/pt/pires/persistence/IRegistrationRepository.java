package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.Registration;

public interface IRegistrationRepository extends CrudRepository<Registration, Long> {

}
