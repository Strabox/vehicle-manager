package com.pt.pires.initialization;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pt.pires.domain.License;
import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.Vehicle;
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
	
	private void initialize() throws Exception{
		//Initializations...
		LicensedVehicle l = new LicensedVehicle("Carrinha Mitsubishi", "lol",
				new License("89-30-OZ", new Date()));
		UnlicensedVehicle u = new UnlicensedVehicle("Tractor Verde Fergunson", "Fergunson");
		rr.save(u);
		rr.save(l);
	}
	
}
