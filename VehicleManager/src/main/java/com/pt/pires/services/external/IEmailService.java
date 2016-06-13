package com.pt.pires.services.external;

public interface IEmailService {

	boolean sendEmail(String gmailUsername,String gmailPassowrd,
			String from,String to,String subject,String body);
	
}
