package com.pt.pires.services.local;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.InvalidEmailException;
import com.pt.pires.domain.exceptions.InvalidPasswordException;
import com.pt.pires.domain.exceptions.InvalidUsernameException;
import com.pt.pires.domain.exceptions.UserAlreadyExistsException;
import com.pt.pires.domain.exceptions.UserDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.UserRepository;
import com.pt.pires.security.SecurityUtil;

@Service("userService")
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	/**
	 * @throws UserAlreadyExistsException 
	 * @throws InvalidUsernameException 
	 * @throws InvalidPasswordException 
	 * @throws InvalidEmailException 
	 * 
	 */
	@Override
	@Transactional(readOnly = false)
	public void createUser(String username, String plainPassword,String email,UserRole role) 
			throws UserAlreadyExistsException, InvalidUsernameException, InvalidPasswordException,
			InvalidEmailException {
		if(username == null || plainPassword == null || email == null || role == null){
			throw new IllegalArgumentException();
		}
		if(userRepository.exists(username)) {
			throw new UserAlreadyExistsException();
		}
		else if(plainPassword.length() < User.MINIMUM_PASSWORD_LENGTH) {
			throw new InvalidPasswordException();
		}
		String encodedPassword = SecurityUtil.passwordEncoder().encode(plainPassword);
		User user = new User(username,encodedPassword,email,role);
		userRepository.save(user);
	}

	/**
	 * Remove a user given his username (ID) if it already exists otherwise
	 * do nothing.
	 * @param String User's username
	 * @throws UserDoesntExistException 
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeUser(String username) throws UserDoesntExistException  {
		if(username == null) {
			throw new IllegalArgumentException();
		}
		if(userRepository.exists(username)) {
			userRepository.delete(username);
		}
		else {
			throw new UserDoesntExistException();
		}
	}
	
	/**
	 * Get a collection of users that have the given role
	 * @param UserRole User's role
	 * @return User's collection
	 */
	@Override
	@Transactional(readOnly = true)
	public Collection<User> getUserByRole(UserRole role) throws VehicleManagerException{
		if(role == null) {
			throw new IllegalArgumentException();
		}
		return userRepository.findByRole(role);
	}

}
