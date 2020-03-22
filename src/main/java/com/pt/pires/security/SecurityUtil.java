package com.pt.pires.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityUtil {

	/**
	 * Return the password encoder used to "Hash"/Encode passwords.
	 * Encode uses a 16-bit RANDOM salt so we can generate different 
	 * encodes for the same input.
	 * @return
	 */
	public static PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
}
