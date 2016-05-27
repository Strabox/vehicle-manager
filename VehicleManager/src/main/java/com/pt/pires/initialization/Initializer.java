package com.pt.pires.initialization;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.pt.pires.domain.License;
import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.repositories.LicensedVehicleRepository;
import com.pt.pires.repositories.VehicleRepository;


/**
 * Where we setup the application needs.
 * @author André
 *
 */
@Component
public class Initializer {

	@Autowired
	private VehicleRepository r;
	
	@Autowired
	private LicensedVehicleRepository r2;
	
	@EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() throws Exception {
        initialize();
    }
	
	private void initialize() throws Exception{
		//Initializations...
		LicensedVehicle l = new LicensedVehicle("WOw", "lol",new License("HH-HH-22", new Date()));
		UnlicensedVehicle u = new UnlicensedVehicle("wa", "nice");
		r.save(l);
		r.save(u);
		LicensedVehicle l1 = r2.findOne("WOw");
		if(l1 != null)
			System.out.println(l1.getLicense().getLicense());
	}
	
}
