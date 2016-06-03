package com.pt.pires.unit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.License;
import com.pt.pires.domain.exceptions.InvalidLicenseException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public class LicenseTest {

	private final static String VALID_LICENSE_1 = "44-11-HH";
	private final static String VALID_LICENSE_2 = "11-GG-19";
	private final static String VALID_LICENSE_3 = "KI-02-45";
	
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
	
	@Test
	public void validLicense1() throws InvalidLicenseException{
		License license = new License(VALID_LICENSE_1,new Date());
		assertTrue(license.getLicense().equals(VALID_LICENSE_1));
	}
	
	@Test
	public void validLicense2() throws InvalidLicenseException{
		License license = new License(VALID_LICENSE_2,new Date());
		assertTrue(license.getLicense().equals(VALID_LICENSE_2));
	}
	
	@Test
	public void validLicense3() throws InvalidLicenseException{
		License license = new License(VALID_LICENSE_3,new Date());
		assertTrue(license.getLicense().equals(VALID_LICENSE_3));
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense1() throws InvalidLicenseException{
		new License(INVALID_LICENSE_1,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense2() throws InvalidLicenseException{
		new License(INVALID_LICENSE_2,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense3() throws InvalidLicenseException{
		new License(INVALID_LICENSE_3,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense4() throws InvalidLicenseException{
		new License(INVALID_LICENSE_4,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense5() throws InvalidLicenseException{
		new License(INVALID_LICENSE_5,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense6() throws InvalidLicenseException{
		new License(INVALID_LICENSE_6,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense7() throws InvalidLicenseException{
		new License(INVALID_LICENSE_7,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense8() throws InvalidLicenseException{
		new License(INVALID_LICENSE_8,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense9() throws InvalidLicenseException{
		new License(INVALID_LICENSE_9,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense10() throws InvalidLicenseException{
		new License(INVALID_LICENSE_10,new Date());
	}
}
