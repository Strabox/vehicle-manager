package com.pt.pires.services.external;

public interface IEmailService {

	boolean sendEmail(String username,String password,
			String from,String to,String subject,String body);
	
}
