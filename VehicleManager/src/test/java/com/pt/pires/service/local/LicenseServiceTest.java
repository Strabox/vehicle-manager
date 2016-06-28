package com.pt.pires.service.local;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.services.local.ILicenseService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public class LicenseServiceTest {

	@Autowired
	@Qualifier("licenseService")
	private ILicenseService licenseService;
	
	
	@Test(expected = IllegalArgumentException.class)
	public void validateLicenseNullLicense() {
		licenseService.validateLicense(null);
	}
	
}
