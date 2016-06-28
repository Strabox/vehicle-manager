package com.pt.pires.service.integration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.exceptions.ImpossibleSaveFileException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.external.IFileService;
import com.pt.pires.services.integrator.IVehicleIntegratorService;
import com.pt.pires.services.integrator.VehicleIntegratorService;
import com.pt.pires.services.local.IVehicleService;
import com.pt.pires.util.DateUtil;

import org.mockito.MockitoAnnotations;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class VehicleIntegratorServiceTest extends VehicleManagerServiceTest{

	private static final String VEHICLE_NAME = "Carro cor de rosa";
	private static final String VEHICLE_EXISTING_NAME = "Calhambeco do noddy";
	private static final String VEHICLE_DOESNT_EXIST_NAME = "Calhambeco do pussas";
	private static final String VEHICLE_BRAND = "Skoda";
	private static final Date VEHICLE_DATE = DateUtil.getSimplifyDate(new Date());
	
	private static final String VALID_LICENSE = "GG-56-90";
	
	private static final String IMAGE_PNG_TEST_RESOURCE_PATH = "/ImagePNG.png";
	private static byte[] imagePNG = null;
	
	private static final String PDF_TEST_RESOURCE_PATH = "/filePDF.pdf";
	private static byte[] filePDF = null;
	
	private IVehicleIntegratorService vehicleIntegratorService;
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService locaVehicleService;
	
	@Mock
	private IFileService fileService;
	
	
	@BeforeClass
	public static void populateFiles(){
		try {
			imagePNG = Files.readAllBytes(
					Paths.get(new ClassPathResource(IMAGE_PNG_TEST_RESOURCE_PATH).getFile().getAbsolutePath()));
			filePDF = Files.readAllBytes(
					Paths.get(new ClassPathResource(PDF_TEST_RESOURCE_PATH).getFile().getAbsolutePath()));
		} catch (IOException e) {
			System.err.println("[ERROR: Error reading test resource files]");
			e.printStackTrace();
		}
	}
	
	@Override
	@Before
	public void populate() throws VehicleManagerException {
		MockitoAnnotations.initMocks(this);		//Setup Mockito annotations beacause
		vehicleIntegratorService = new VehicleIntegratorService(locaVehicleService, fileService);
		newUnlicensedVehicle(VEHICLE_EXISTING_NAME, VEHICLE_BRAND, VEHICLE_DATE);
	}
	
	/* ================== CreateUnlicensedVehicle Integrator Service ==================== */
	
	@Test
	public void createUnlicensedVehicleWithValidImage() throws VehicleManagerException{
		vehicleIntegratorService.createUnlicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND,
				VEHICLE_DATE, true, imagePNG);
		VehicleUnlicensed v = obtainUnlicensedVehicle(VEHICLE_NAME);
		Mockito.verify(fileService,Mockito.times(1)).addPortraitImage(VEHICLE_NAME, imagePNG);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND));
	}
	
	@Test
	public void createUnlicensedVehicleWithInvalidImage() throws VehicleManagerException{
		Mockito.doThrow(new ImpossibleSaveFileException()).when(fileService).addPortraitImage(VEHICLE_NAME, filePDF);
		try{
		vehicleIntegratorService.createUnlicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND,
				VEHICLE_DATE, true, filePDF);
		} catch(ImpossibleSaveFileException e){
			Assert.assertNull(obtainUnlicensedVehicle(VEHICLE_NAME));
		}
		Mockito.verify(fileService,Mockito.times(1)).addPortraitImage(VEHICLE_NAME, filePDF);
	}
	
	@Test
	public void createUnlicensedVehicleWithNoImage() throws VehicleManagerException{
		vehicleIntegratorService.createUnlicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND,
				VEHICLE_DATE, false, null);
		Mockito.verify(fileService,Mockito.never()).addPortraitImage(VEHICLE_NAME, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullVehicleName() throws VehicleManagerException{
		vehicleIntegratorService.createUnlicensedVehicle(null, VEHICLE_BRAND,
				VEHICLE_DATE, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullBrand() throws VehicleManagerException{
		vehicleIntegratorService.createUnlicensedVehicle(VEHICLE_NAME, null,
				VEHICLE_DATE, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullDate() throws VehicleManagerException{
		vehicleIntegratorService.createUnlicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND,
				null, true, imagePNG);
	}
	
	/* ============= CreateLicensedVehicle Integrator Service ========================== */
	
	@Test
	public void createLicensedVehicleWithValidImage() throws VehicleManagerException{
		vehicleIntegratorService.createLicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE, VEHICLE_DATE, true, imagePNG);
		VehicleLicensed v = obtainLicensedVehicle(VEHICLE_NAME);
		Mockito.verify(fileService,Mockito.times(1)).addPortraitImage(VEHICLE_NAME, imagePNG);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE));
	}
	
	@Test
	public void createLicensedVehicleWithInvalidImage() throws VehicleManagerException{
		Mockito.doThrow(new ImpossibleSaveFileException()).when(fileService).addPortraitImage(VEHICLE_NAME, filePDF);
		try{
		vehicleIntegratorService.createLicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE, VEHICLE_DATE, true, filePDF);
		} catch(ImpossibleSaveFileException e){
			Assert.assertNull(obtainLicensedVehicle(VEHICLE_NAME));
		}
		Mockito.verify(fileService,Mockito.times(1)).addPortraitImage(VEHICLE_NAME, filePDF);
	}
	
	@Test
	public void createLicensedVehicleWithNoImage() throws VehicleManagerException{
		vehicleIntegratorService.createLicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE, VEHICLE_DATE, false, null);
		Mockito.verify(fileService,Mockito.never()).addPortraitImage(VEHICLE_NAME, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullVehicleName() throws VehicleManagerException{
		vehicleIntegratorService.createLicensedVehicle(null, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE, VEHICLE_DATE, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullBrand() throws VehicleManagerException{
		vehicleIntegratorService.createLicensedVehicle(VEHICLE_NAME, null, VEHICLE_DATE, VALID_LICENSE,
				VEHICLE_DATE, false, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullAcquisitonDate() throws VehicleManagerException{
		vehicleIntegratorService.createLicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, null,
				VALID_LICENSE, VEHICLE_DATE, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullLicenseDate() throws VehicleManagerException{
		vehicleIntegratorService.createLicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE, null, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullLicense() throws VehicleManagerException{
		vehicleIntegratorService.createLicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, VEHICLE_DATE,
				null, VEHICLE_DATE, true, imagePNG);
	}
	
	/* =========================== RemoveVehicle Integrator Service ====================== */
	
	@Test
	public void removeVehicleExisting() throws VehicleManagerException{
		vehicleIntegratorService.removeVehicle(VEHICLE_EXISTING_NAME);
		Mockito.verify(fileService,Mockito.times(1)).removeVehicleFiles(VEHICLE_EXISTING_NAME);
	}
	
	@Test
	public void removeVehicleDoesntExist() throws VehicleManagerException{
		vehicleIntegratorService.removeVehicle(VEHICLE_DOESNT_EXIST_NAME);
		Mockito.verify(fileService,Mockito.times(1)).removeVehicleFiles(VEHICLE_DOESNT_EXIST_NAME);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeVehicleNullVehicleName() throws VehicleManagerException{
		vehicleIntegratorService.removeVehicle(null);
	}
	
	/* =================== AddOrUpdatePortraitImage Integrator Service ====================== */
	 
	public void addOrUpdatePortraitImage() throws VehicleManagerException{
		vehicleIntegratorService.addOrUpdateVehiclePortrait(VEHICLE_EXISTING_NAME, imagePNG);
		Mockito.verify(fileService).addPortraitImage(VEHICLE_EXISTING_NAME, imagePNG);;
	}
	
	public void addOrUpdatePortraitImageNullVehicleName() throws VehicleManagerException{
		vehicleIntegratorService.addOrUpdateVehiclePortrait(null, imagePNG);
	}
	
	public void addOrUpdatePortraitImageNullImage() throws VehicleManagerException{
		vehicleIntegratorService.addOrUpdateVehiclePortrait(VEHICLE_EXISTING_NAME, null);
	}
	
}
