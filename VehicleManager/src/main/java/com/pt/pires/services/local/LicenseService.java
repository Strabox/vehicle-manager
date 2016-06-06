package com.pt.pires.services.local;

import org.springframework.stereotype.Service;

import com.pt.pires.domain.License;

@Service
public class LicenseService implements ILicenseService{

	@Override
	public boolean validateLicense(String license){
		return License.validateLicense(license);
	}
	
}
