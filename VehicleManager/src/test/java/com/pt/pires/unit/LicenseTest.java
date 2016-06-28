package com.pt.pires.unit;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
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
	
	/* =============== License Validation =================== */
	
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
	
	@Test
	public void validLicense4() throws InvalidLicenseException{
		License license = new License(VALID_LICENSE_4,new Date());
		assertTrue(license.getLicense().equals(VALID_LICENSE_4));
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
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense11() throws InvalidLicenseException{
		new License(INVALID_LICENSE_11,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense12() throws InvalidLicenseException{
		new License(INVALID_LICENSE_12,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense13() throws InvalidLicenseException{
		new License(INVALID_LICENSE_13,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense14() throws InvalidLicenseException{
		new License(INVALID_LICENSE_14,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense15() throws InvalidLicenseException{
		new License(INVALID_LICENSE_15,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense16() throws InvalidLicenseException{
		new License(INVALID_LICENSE_16,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense17() throws InvalidLicenseException{
		new License(INVALID_LICENSE_17,new Date());
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void invalidLicense19() throws InvalidLicenseException{
		new License("",new Date());
	}
	
	/* ================== Others =================== */
	
	@Test
	public void licenseYears() throws InvalidLicenseException{
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		int currentYear = c1.get(Calendar.YEAR);
		int currentMonth = c1.get(Calendar.MONTH);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.YEAR,currentYear - 10);
		c2.set(Calendar.MONTH,(currentMonth - 1) % 11);
		License l = new License(VALID_LICENSE_1, c2.getTime());
		Assert.assertTrue(l.calculateLicenseYears() == 10);
	}
	
	@Test
	public void licenseYears2() throws InvalidLicenseException{
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		int currentYear = c1.get(Calendar.YEAR);
		int currentMonth = c1.get(Calendar.MONTH);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.YEAR,currentYear - 10);
		c2.set(Calendar.MONTH,(currentMonth + 1) % 11);
		License l = new License(VALID_LICENSE_1, c2.getTime());
		Assert.assertTrue(l.calculateLicenseYears() == 9);
	}
	
	/* ============================================== */
	
}
