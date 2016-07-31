package com.pt.pires.service.integration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import org.mockito.MockitoAnnotations;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class VehicleIntegratorServiceTest extends VehicleManagerServiceTest {

	private static final String VEHICLE_EXISTING_NAME = "Calhambeco do noddy";
	private static final String VEHICLE_DOESNT_EXIST_NAME = "Calhambeco do pussas";
	
	private static final String IMAGE_PNG_TEST_RESOURCE_PATH = "/ImagePNG.png";
	private static byte[] imagePNG = null;
	
	private static final String PDF_TEST_RESOURCE_PATH = "/filePDF.pdf";
	private static byte[] filePDF = null;
	
	private IVehicleIntegratorService vehicleIntegratorService;
	
	@Inject
	@Named("vehicleService")
	private IVehicleService locaVehicleService;
	
	@Mock
	private IFileService fileService;
	
	
	@BeforeClass
	public static void populateFiles() {
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
		MockitoAnnotations.initMocks(this);		//IMPORTANT: Setup Mockito annotations because !!!
		vehicleIntegratorService = new VehicleIntegratorService(locaVehicleService, fileService);
		newUnlicensedVehicle(VEHICLE_EXISTING_NAME, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,VALID_FABRICATION_YEAR);
	}
	
	/* ================== CreateUnlicensedVehicle Integrator Service ==================== */
	
	@Test
	public void createUnlicensedVehicleWithValidImage() throws VehicleManagerException {
		vehicleIntegratorService.createUnlicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, VALID_FABRICATION_YEAR, true, imagePNG);
		VehicleUnlicensed v = obtainUnlicensedVehicle(VALID_VEHICLE_NAME);
		Mockito.verify(fileService,Mockito.times(1)).addOrReplaceVehiclePortraitImage(VALID_VEHICLE_NAME, imagePNG);
		Assert.assertTrue(v.getName().equals(VALID_VEHICLE_NAME));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
	}
	
	@Test
	public void createUnlicensedVehicleWithInvalidImage() throws VehicleManagerException {
		Mockito.doThrow(new ImpossibleSaveFileException()).when(fileService).addOrReplaceVehiclePortraitImage(VALID_VEHICLE_NAME, filePDF);
		try {
		vehicleIntegratorService.createUnlicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, VALID_FABRICATION_YEAR, true, filePDF);
		} catch(ImpossibleSaveFileException e) {
			Assert.assertNull(obtainUnlicensedVehicle(VALID_VEHICLE_NAME));
		}
		Mockito.verify(fileService,Mockito.times(1)).addOrReplaceVehiclePortraitImage(VALID_VEHICLE_NAME, filePDF);
	}
	
	@Test
	public void createUnlicensedVehicleWithNoImage() throws VehicleManagerException {
		vehicleIntegratorService.createUnlicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, VALID_FABRICATION_YEAR, false, null);
		Mockito.verify(fileService,Mockito.never()).addOrReplaceVehiclePortraitImage(VALID_VEHICLE_NAME, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullVehicleName() throws VehicleManagerException {
		vehicleIntegratorService.createUnlicensedVehicle(null, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, VALID_FABRICATION_YEAR, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullBrand() throws VehicleManagerException {
		vehicleIntegratorService.createUnlicensedVehicle(VALID_VEHICLE_NAME, null,
				VALID_CURRENT_DATE, VALID_FABRICATION_YEAR, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullDate() throws VehicleManagerException {
		vehicleIntegratorService.createUnlicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND,
				null, VALID_FABRICATION_YEAR, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullImage() throws VehicleManagerException {
		vehicleIntegratorService.createUnlicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, VALID_FABRICATION_YEAR, true, null);
	}
	
	/* ============= CreateLicensedVehicle Integrator Service ========================== */
	
	@Test
	public void createLicensedVehicleWithValidImage() throws VehicleManagerException {
		vehicleIntegratorService.createLicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE, VALID_CURRENT_DATE, true, imagePNG);
		VehicleLicensed v = obtainLicensedVehicle(VALID_VEHICLE_NAME);
		Mockito.verify(fileService,Mockito.times(1)).addOrReplaceVehiclePortraitImage(VALID_VEHICLE_NAME, imagePNG);
		Assert.assertTrue(v.getName().equals(VALID_VEHICLE_NAME));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE));
	}
	
	@Test
	public void createLicensedVehicleWithInvalidImage() throws VehicleManagerException {
		Mockito.doThrow(new ImpossibleSaveFileException()).when(fileService).addOrReplaceVehiclePortraitImage(VALID_VEHICLE_NAME, filePDF);
		try {
		vehicleIntegratorService.createLicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE, VALID_CURRENT_DATE, true, filePDF);
		} catch(ImpossibleSaveFileException e) {
			Assert.assertNull(obtainLicensedVehicle(VALID_VEHICLE_NAME));
		}
		Mockito.verify(fileService,Mockito.times(1)).addOrReplaceVehiclePortraitImage(VALID_VEHICLE_NAME, filePDF);
	}
	
	@Test
	public void createLicensedVehicleWithNoImage() throws VehicleManagerException {
		vehicleIntegratorService.createLicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE, VALID_CURRENT_DATE, false, null);
		Mockito.verify(fileService,Mockito.never()).addOrReplaceVehiclePortraitImage(VALID_VEHICLE_NAME, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullVehicleName() throws VehicleManagerException {
		vehicleIntegratorService.createLicensedVehicle(null, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE, VALID_CURRENT_DATE, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullBrand() throws VehicleManagerException {
		vehicleIntegratorService.createLicensedVehicle(VALID_VEHICLE_NAME, null, VALID_CURRENT_DATE, VALID_LICENSE,
				VALID_CURRENT_DATE, false, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullAcquisitonDate() throws VehicleManagerException {
		vehicleIntegratorService.createLicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, null,
				VALID_LICENSE, VALID_CURRENT_DATE, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullLicenseDate() throws VehicleManagerException {
		vehicleIntegratorService.createLicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE, null, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullLicense() throws VehicleManagerException {
		vehicleIntegratorService.createLicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				null, VALID_CURRENT_DATE, true, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullImage() throws VehicleManagerException {
		vehicleIntegratorService.createLicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE, VALID_CURRENT_DATE, true, null);
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
	 
	@Test
	public void addOrUpdatePortraitImage() throws VehicleManagerException{
		vehicleIntegratorService.addOrUpdateVehiclePortrait(VEHICLE_EXISTING_NAME, imagePNG);
		Mockito.verify(fileService,Mockito.times(1)).addOrReplaceVehiclePortraitImage(VEHICLE_EXISTING_NAME, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addOrUpdatePortraitImageNullVehicleName() throws VehicleManagerException{
		vehicleIntegratorService.addOrUpdateVehiclePortrait(null, imagePNG);
		Mockito.verify(fileService,Mockito.times(0)).addOrReplaceVehiclePortraitImage(null, imagePNG);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addOrUpdatePortraitImageNullImage() throws VehicleManagerException{
		vehicleIntegratorService.addOrUpdateVehiclePortrait(VEHICLE_EXISTING_NAME, null);
		Mockito.verify(fileService,Mockito.times(0)).addOrReplaceVehiclePortraitImage(VEHICLE_EXISTING_NAME, null);
	}
	
	/* =================== GetVehiclePortraitImage Integrator Service ====================== */
	
	@Test
	public void getVehicleIntegratorService() throws VehicleManagerException{
		vehicleIntegratorService.getVehiclePortraitImage(VEHICLE_EXISTING_NAME);
		Mockito.verify(fileService,Mockito.times(1)).getVehiclePortraitImage(VEHICLE_EXISTING_NAME);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getVehicleIntegratorServiceNullVehicleName() throws VehicleManagerException{
		vehicleIntegratorService.getVehiclePortraitImage(null);
		Mockito.verify(fileService,Mockito.times(0)).getVehiclePortraitImage(VEHICLE_EXISTING_NAME);
	}
}
