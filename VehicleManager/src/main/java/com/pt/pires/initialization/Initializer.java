package com.pt.pires.initialization;

import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pt.pires.domain.License;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.UserRepository;
import com.pt.pires.persistence.VehicleRepository;
import com.pt.pires.security.SecurityUtil;


/**
 * Where we setup the application needs.
 * @author André
 *
 */
@Component
public class Initializer {
	
	@Autowired
	private VehicleRepository rr;
	
	@Autowired
	private UserRepository ur;
	
	public void initialize() throws Exception{
		//Initializations...
		System.out.println("===== Initializing Database ======");
		try{
			User user = new User("jose",SecurityUtil.passwordEncoder().encode("pires"));
			user.setRole(UserRole.ROLE_USER);
			ur.save(user);
			Calendar c = Calendar.getInstance();
			c.set(Calendar.YEAR,2000);
			Date d = c.getTime();
			VehicleLicensed l = new VehicleLicensed("Carrinha Mitsubishi", "lol",d,
					new License("89-30-OZ", new Date()));
			c.set(Calendar.YEAR,2006);
			d = c.getTime();
			VehicleUnlicensed u = new VehicleUnlicensed("Tractor Verde Fergunson", "Fergunson",d);
			l.addRegistration(new Registration(100, "Mudança de óleo", new Date()));
			l.addRegistration(new Registration(400, "Mudança de óleo", new Date()));
			l.addRegistration(new Registration(550, "Mudança de filtro", new Date()));
			l.addNote(new Note("Marca do filtro"));
			l.addNote(new Note("Tamanho do filtro bla bla bla"));
			rr.save(u);
			rr.save(l);
		}catch(VehicleManagerException e){
			//DO nothing
		}
		System.out.println("===== Database Initialized ======");
	}
	
}
