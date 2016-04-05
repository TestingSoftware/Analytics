package com.ncubo.analytics.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo {
	
	private static Properties mailServerProperties;
	private static Session getMailSession;
	private static MimeMessage generateMailMessage;
	
	private final String USUARIO = "dflores@cecropiasolutions.com";
	private final String PASSWORD = "Asdasdasd1!";
	private String asunto = "Prueba correo";
	
	public void enviarCorreo() throws AddressException, MessagingException
	{
		String para = "dalaianxd@gmail.com";
		String copiaA = "vcenteno@cecropiasolutions.com";
		String emailBody = "";
		
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");

		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		Transport transport = getMailSession.getTransport("smtp");
		transport.connect("smtp.gmail.com", USUARIO, PASSWORD);
		
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(para));
		//generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(copiaA));
		generateMailMessage.setSubject(asunto);
		generateMailMessage.setContent(emailBody, "text/html");

		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}

}
