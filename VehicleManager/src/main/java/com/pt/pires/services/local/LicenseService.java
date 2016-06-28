package com.pt.pires.services.local;

import org.springframework.stereotype.Service;

import com.pt.pires.domain.License;

@Service("licenseService")
public class LicenseService implements ILicenseService {

	@Override
	public boolean validateLicense(String license) {
		if(license == null) {
			throw new IllegalArgumentException();
		}
		return License.validateLicense(license);
	}
	
}
