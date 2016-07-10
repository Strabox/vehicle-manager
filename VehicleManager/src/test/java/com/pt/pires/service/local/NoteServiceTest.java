package com.pt.pires.service.local;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.exceptions.InvalidNoteException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.local.INoteService;
import com.pt.pires.util.DateUtil;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class NoteServiceTest extends VehicleManagerServiceTest  {

	@Autowired
	@Qualifier("noteService")
	private INoteService noteService;
	
	private final static String VEHICLE_NAME_DOESNT_EXIST_2 = "Automóvel";
	private final static String VEHICLE_NAME_EXIST_1 = "Popó amarelo";
	private final static String VEHICLE_NAME_EXIST_2 = "Carro Azul";
	
	private final static String VEHICLE_BRAND_1 = "Skoda";
	
	private final static Date VEHICLE_DATE = DateUtil.getSimplifyDate(new Date());
	
	private final static int FABRICATION_YEAR = 1994;
	
	private final static String VALID_LICENSE = "55-11-KI";
	
	private final static String VALID_DESCRIPTION = "Mudar o óleo do Motor!!";
	
	private static Long EXISTING_NOTE_ID_1;
	
	@Override
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_EXIST_1,VEHICLE_BRAND_1,VEHICLE_DATE, FABRICATION_YEAR);
		EXISTING_NOTE_ID_1 = newNote(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION);
		newLicensedVehicle(VEHICLE_NAME_EXIST_2, VEHICLE_BRAND_1, VEHICLE_DATE, VALID_LICENSE, VEHICLE_DATE);
	}

/* =================== AddNoteToVehicleService ============== */
	
	@Test
	public void createNote() throws VehicleManagerException{
		Long noteId = noteService.createNote(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION);
		List<Note> notes = obtainNotes(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(notes);
		Assert.assertTrue(obtainNote(VEHICLE_NAME_EXIST_1, noteId).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void createNoteVehicleDontExist() throws VehicleManagerException{
		noteService.createNote(VEHICLE_NAME_DOESNT_EXIST_2, VALID_DESCRIPTION);
	}
	
	@Test(expected = InvalidNoteException.class)
	public void createNoteInvalidNote() throws VehicleManagerException{
		noteService.createNote(VEHICLE_NAME_EXIST_1, EMPTY_STRING);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createNoteNullName() throws VehicleManagerException{
		noteService.createNote(null, EMPTY_STRING);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createNoteNullNote() throws VehicleManagerException{
		noteService.createNote(VEHICLE_NAME_EXIST_1, null);
	}
	
	/* =================== RemoveNoteFromVehicleService ================= */
	
	@Test
	public void removeNote() throws VehicleManagerException{
		noteService.removeNote(VEHICLE_NAME_EXIST_1, EXISTING_NOTE_ID_1);
		Assert.assertNotNull(obtainNotes(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(obtainNotes(VEHICLE_NAME_EXIST_1).isEmpty());
		Assert.assertNull(obtainNote(VEHICLE_NAME_EXIST_1, EXISTING_NOTE_ID_1));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void removeNoteVehicleDoesntExist() throws VehicleManagerException{
		noteService.removeNote(VEHICLE_NAME_DOESNT_EXIST_2, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeNoteNullName() throws VehicleManagerException{
		noteService.removeNote(null, 1);
	}
	
}
