package com.pt.pires.service.local;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.services.local.ILicenseService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public class LicenseServiceTest {

	private final static String VALID_LICENSE_1 = "44-11-HH";
	private final static String VALID_LICENSE_2 = "11-GG-19";
	private final static String VALID_LICENSE_3 = "KI-02-45";
	private final static String VALID_LICENSE_4 = "ZZ-00-99";
	
	private final static String INVALID_LICENSE_1 = "AG-GG-21";
	private final static String INVALID_LICENSE_2 = "AV-22-TY";
	private final static String INVALID_LICENSE_3 = "AASDSDAD";
	private final static String INVALID_LICENSE_4 = "AV-22-1Y";
	private final static String INVALID_LICENSE_5 = "11-AJ-TY";
	private final static String INVALID_LICENSE_6 = "11-22-TYA";
	private final static String INVALID_LICENSE_7 = "111-22-TY";
	private final static String INVALID_LICENSE_8 = "11-212-TY";
	private final static String INVALID_LICENSE_9 = "11-22-yY";
	private final static String INVALID_LICENSE_10 = "11--22-TY";
	private final static String INVALID_LICENSE_11 = "<<--22-TY";
	private final static String INVALID_LICENSE_12 = "AA-22-111";
	private final static String INVALID_LICENSE_13 = "11-HJ-451";
	private final static String INVALID_LICENSE_14 = "11-AHJ-45";
	private final static String INVALID_LICENSE_15 = " AA-11-41";
	private final static String INVALID_LICENSE_16 = " 11-HJ-41";
	private final static String INVALID_LICENSE_17 = " 11-32-RT";
	
	@Inject
	@Named("licenseService")
	private ILicenseService licenseService;
	
	
	@Test
	public void validateLicenseValid_1() {
		Assert.assertTrue(licenseService.validateLicense(VALID_LICENSE_1));
	}
	
	@Test
	public void validateLicenseValid_2() {
		Assert.assertTrue(licenseService.validateLicense(VALID_LICENSE_2));
	}
	
	@Test
	public void validateLicenseValid_3() {
		Assert.assertTrue(licenseService.validateLicense(VALID_LICENSE_3));
	}
	
	@Test
	public void validateLicenseValid_4() {
		Assert.assertTrue(licenseService.validateLicense(VALID_LICENSE_4));
	}
	
	@Test
	public void validateLicenseInvalid_1() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_1));
	}
	
	@Test
	public void validateLicenseInvalid_2() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_2));
	}
	
	@Test
	public void validateLicenseInvalid_3() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_3));
	}
	
	@Test
	public void validateLicenseInvalid_4() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_4));
	}
	
	@Test
	public void validateLicenseInvalid_5() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_5));
	}
	
	@Test
	public void validateLicenseInvalid_6() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_6));
	}
	
	@Test
	public void validateLicenseInvalid_7() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_7));
	}
	
	@Test
	public void validateLicenseInvalid_8() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_8));
	}
	
	@Test
	public void validateLicenseInvalid_9() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_9));
	}
	
	@Test
	public void validateLicenseInvalid_10() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_10));
	}
	
	@Test
	public void validateLicenseInvalid_11() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_11));
	}
	
	@Test
	public void validateLicenseInvalid_12() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_12));
	}
	
	@Test
	public void validateLicenseInvalid_13() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_13));
	}
	
	@Test
	public void validateLicenseInvalid_14() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_14));
	}
	
	@Test
	public void validateLicenseInvalid_15() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_15));
	}
	
	@Test
	public void validateLicenseInvalid_16() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_16));
	}
	
	@Test
	public void validateLicenseInvalid_17() {
		Assert.assertFalse(licenseService.validateLicense(INVALID_LICENSE_17));
	}
	
	@Test
	public void validateLicenseInvalid_18() {
		Assert.assertFalse(licenseService.validateLicense(""));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void validateLicenseNullLicense() {
		licenseService.validateLicense(null);
	}
	
}
