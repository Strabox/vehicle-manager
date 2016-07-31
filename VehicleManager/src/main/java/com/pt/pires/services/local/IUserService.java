package com.pt.pires.services.local;

import java.util.Collection;

import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.VehicleManagerException;

/**
 * Service related to users
 * @author André
 *
 */
public interface IUserService {

	void createUser(String username,String plainPassword,String email,UserRole role) throws VehicleManagerException;
	
	void removeUser(String username) throws VehicleManagerException;
	
	Collection<User> getUsersByRole(UserRole role);
	
}
