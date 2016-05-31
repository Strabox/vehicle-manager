package com.pt.pires.initialization;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pt.pires.domain.License;
import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.persistence.VehicleRepository;


/**
 * Where we setup the application needs.
 * @author André
 *
 */
@Component
public class Initializer {
	
	@Autowired
	private VehicleRepository rr;
	
	@EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() throws Exception {
        initialize();
    }
	
	public void initialize() throws Exception{
		//Initializations...
		System.out.println("===== Initializing Database ======");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR,2000);
		Date d = c.getTime();
		LicensedVehicle l = new LicensedVehicle("Carrinha Mitsubishi", "lol",d,
				new License("89-30-OZ", new Date()));
		c.set(Calendar.YEAR,2006);
		d = c.getTime();
		UnlicensedVehicle u = new UnlicensedVehicle("Tractor Verde Fergunson", "Fergunson",d);
		l.addRegistration(new Registration(100, "Mudança de óleo", new Date()));
		l.addRegistration(new Registration(400, "Mudança de óleo", new Date()));
		l.addRegistration(new Registration(550, "Mudança de filtro", new Date()));
		l.addNote(new Note("Marca do filtro"));
		l.addNote(new Note("Tamanho do filtro bla bla bla"));
		rr.save(u);
		rr.save(l);
		System.out.println("===== Database Initialized ======");
	}
	
}
