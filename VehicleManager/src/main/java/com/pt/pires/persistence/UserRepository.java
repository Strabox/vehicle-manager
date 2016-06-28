package com.pt.pires.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;

public interface UserRepository extends CrudRepository<User,String>  {

	List<User> findByRole(UserRole role); 
	
}
