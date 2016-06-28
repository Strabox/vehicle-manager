package com.pt.pires.service.local;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.InvalidEmailException;
import com.pt.pires.domain.exceptions.InvalidPasswordException;
import com.pt.pires.domain.exceptions.InvalidUsernameException;
import com.pt.pires.domain.exceptions.UserAlreadyExistsException;
import com.pt.pires.domain.exceptions.UserDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.security.SecurityUtil;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.local.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class UserServiceTest extends VehicleManagerServiceTest {

	private static final String USERNAME_DOESNT_EXIST = "Strabox";
	private static final String USERNAME_EXIST = "Afpp";
	private static final String[] INVALID_USERNAMES = { EMPTY_STRING , "a", "sp", "dad" };
	
	private static final String VALID_PASSWORD = "suchwow"; 
	private static final String[] INVALID_PASSWORDS = { EMPTY_STRING, "a", "sp", "dad"};
	
	private static final String EMAIL = "no-reply@gmail.com";
	private static final String[] INVALID_EMAILS = { EMPTY_STRING, "no-replia.com", "no-replia@company..co.uk", "dad",
													"andre@", "no@lol" };
	
	private static final UserRole ROLE = UserRole.ROLE_USER;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	
	@Override
	public void populate() throws VehicleManagerException {
		newUser(USERNAME_EXIST, VALID_PASSWORD,EMAIL, ROLE);
	}

	/* ================== CreateUser Service =================== */
	
	@Test
	public void createUserSuccess() throws VehicleManagerException {
		userService.createUser(USERNAME_DOESNT_EXIST, VALID_PASSWORD,EMAIL, ROLE);
		User user = obtainUser(USERNAME_DOESNT_EXIST);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getUsername().equals(USERNAME_DOESNT_EXIST));
		Assert.assertTrue(SecurityUtil.passwordEncoder().matches(VALID_PASSWORD, user.getPassword()));
		Assert.assertTrue(user.getRole() == ROLE);
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void createUserAlreadyExist() throws VehicleManagerException {
		userService.createUser(USERNAME_EXIST, VALID_PASSWORD, EMAIL,ROLE);
	}

	@Test
	public void createUserInvalidUsernames() {
		for(String invalidUsername : INVALID_USERNAMES){
			try {
				userService.createUser(invalidUsername, VALID_PASSWORD, EMAIL,ROLE);
			} catch(InvalidUsernameException e) {
				continue;
			} catch (VehicleManagerException e) {
				throw new RuntimeException("This shouldn't happend in this service");
			}
			throw new AssertionError("Email should be invalid: " + invalidUsername);
		}
	}
	
	@Test
	public void createUserInvalidPasswords() {
		for(String invalidPassword : INVALID_PASSWORDS) {
			try {
				userService.createUser(USERNAME_DOESNT_EXIST, invalidPassword, EMAIL, ROLE);
			} catch(InvalidPasswordException e) {
				continue;
			} catch (VehicleManagerException e) {
				throw new RuntimeException("This shouldn't happend in this service");
			}
			throw new AssertionError("Password should be invalid: " + invalidPassword);
		}
	}
	
	
	@Test
	public void createUserInvalidEmails() {
		for(String invalidEmail : INVALID_EMAILS){
			try {
				userService.createUser(USERNAME_DOESNT_EXIST, VALID_PASSWORD, invalidEmail, ROLE);
			} catch(InvalidEmailException e) {
				continue;
			} catch (VehicleManagerException e) {
				throw new RuntimeException("This shouldn't happend in this service");
			}
			throw new AssertionError("Email should be invalid: " + invalidEmail);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUserNullUsername() throws VehicleManagerException {
		userService.createUser(null, VALID_PASSWORD,EMAIL, ROLE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUserNullPassword() throws VehicleManagerException {
		userService.createUser(USERNAME_DOESNT_EXIST, null,EMAIL, ROLE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUserNullEmail() throws VehicleManagerException {
		userService.createUser(USERNAME_DOESNT_EXIST, VALID_PASSWORD, null, ROLE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUserNullRole() throws VehicleManagerException {
		userService.createUser(USERNAME_DOESNT_EXIST, VALID_PASSWORD,EMAIL, null);
	}
	
	/* ================== RemoveUser Service ===================== */
	
	@Test
	public void removeUserSuccess() throws VehicleManagerException {
		userService.removeUser(USERNAME_EXIST);
		Assert.assertNull(obtainUser(USERNAME_EXIST));
	}
	
	@Test(expected = UserDoesntExistException.class)
	public void removeUserDoesntExist() throws VehicleManagerException {
		userService.removeUser(USERNAME_DOESNT_EXIST);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeUserNullUsername() throws VehicleManagerException {
		userService.removeUser(null);
	}
	
}
