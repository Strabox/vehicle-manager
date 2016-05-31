package com.pt.pires.services;

import org.springframework.stereotype.Service;

import com.pt.pires.domain.License;

@Service
public class LicenseService {

	public boolean validateLicense(String license){
		return License.validateLicense(license);
	}
	
}
