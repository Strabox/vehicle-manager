package com.pt.pires.service.local;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class NoteServiceTest extends VehicleManagerServiceTest  {

	@Inject
	@Named("noteService")
	private INoteService noteService;
	
	private final static String VEHICLE_NAME_DOESNT_EXIST = "Automóvel";
	private final static String VEHICLE_NAME_EXIST_1 = "Pópó Amarelo";
	private final static String VEHICLE_NAME_EXIST_2 = "Carro Azul";
	
	private static Long EXISTING_NOTE_ID_1;
	
	@Override
	@Before
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_EXIST_1, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);
		EXISTING_NOTE_ID_1 = newNote(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION);
		newLicensedVehicle(VEHICLE_NAME_EXIST_2, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE, VALID_LICENSE, VALID_CURRENT_DATE);
	}

	/* =================== CreateNote Service ============== */
	
	@Test
	public void createNoteSuccess() throws VehicleManagerException{
		Long noteId = noteService.createNote(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION);
		List<Note> notes = obtainNotes(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(notes);
		Assert.assertTrue(obtainNote(VEHICLE_NAME_EXIST_1, noteId).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void createNoteVehicleDoesntExist() throws VehicleManagerException{
		noteService.createNote(VEHICLE_NAME_DOESNT_EXIST, VALID_DESCRIPTION);
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
	
	/* =================== RemoveNote Service ================= */
	
	@Test
	public void removeNote() throws VehicleManagerException{
		noteService.removeNote(VEHICLE_NAME_EXIST_1, EXISTING_NOTE_ID_1);
		Assert.assertTrue(obtainNotes(VEHICLE_NAME_EXIST_1).isEmpty());
		Assert.assertNull(obtainNote(VEHICLE_NAME_EXIST_1, EXISTING_NOTE_ID_1));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void removeNoteVehicleDoesntExist() throws VehicleManagerException{
		noteService.removeNote(VEHICLE_NAME_DOESNT_EXIST, Mockito.anyLong());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeNoteNullName() throws VehicleManagerException{
		noteService.removeNote(null, Mockito.anyLong());
	}
	
}
