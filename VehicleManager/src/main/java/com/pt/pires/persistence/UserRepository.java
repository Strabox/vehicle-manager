package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.User;

public interface UserRepository extends CrudRepository<User,String>  {

}
