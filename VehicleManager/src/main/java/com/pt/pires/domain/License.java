package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.util.DateUtil;

/**
 * Represents a vehicle license Portugal style only
 * NN-NN-AA , NN-AA-NN, AA-NN-NN with A for characters and N for numbers from 0 to 9 
 * @author André
 *
 */
@Entity
public class License {

    private static final String LICENSE_1 = "(^[0-9]{2}-[A-Z]{2}-[0-9]{2}$)";
    private static final String LICENSE_2 = "(^[A-Z]{2}-[0-9]{2}-[0-9]{2}$)";
    private static final String LICENSE_3 = "(^[0-9]{2}-[0-9]{2}-[A-Z]{2}$)";
    private static final String LICENSE_PATTERN = LICENSE_1 + "|" + LICENSE_2 + "|" + LICENSE_3;
	
    @Id
	private String license;
	
    @Column
    @Type(type="date")
	private Date date;
	
	public License(String license, Date date) throws InvalidLicenseException {
		setLicense(license);
		setDate(date);
	}
	
	public License() { }	// Needed for JPA/JSON
	
	
	public static boolean validateLicense(String license) {
		return license.matches(LICENSE_PATTERN);
	}
	
	public final int calculateLicenseYears() {
		Calendar licenseCalendar = DateUtil.getCalendar(date);
		Calendar currentCalendar = DateUtil.getCalendar(new Date());
		int diffYears = currentCalendar.get(Calendar.YEAR) - licenseCalendar.get(Calendar.YEAR);
		if(licenseCalendar.get(Calendar.MONTH) > currentCalendar.get(Calendar.MONTH)) {
			diffYears--;
		}
		return diffYears;
	}
	
	/* === Getters and Setters === */
	
	public String getLicense( ){
		return license;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setLicense(String license) throws InvalidLicenseException {
		if(!validateLicense(license)) {
			throw new InvalidLicenseException();
		}
		this.license = license;
	}
	
	@Override
	public boolean equals(Object obj) {
		License license = (License) obj;
		return this.license.equals(license.getLicense()) && 
				(this.date.compareTo(license.getDate()) == 0);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
}
