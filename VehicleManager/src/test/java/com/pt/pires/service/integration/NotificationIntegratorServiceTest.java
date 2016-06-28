package com.pt.pires.service.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.external.IEmailService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class NotificationIntegratorServiceTest extends VehicleManagerServiceTest{

	@Mock
	private IEmailService emailService;
	
	@Override
	public void populate() throws VehicleManagerException {
		// TODO Auto-generated method stub
		
	}
	
	@Test
	public void test(){
		
	}

}
