package com.pt.pires.services.local;

public interface IEmailService {

	boolean sendTLSGmailEmail(String gmailUsername,String gmailPassowrd,
			String from,String to,String subject,String body);
	
}
