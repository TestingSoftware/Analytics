package com.ncubo.analytics.util;

public class Main {

	public static void main(String[] args) throws Exception{
		DataToEmail dataToEmail = new DataToEmail();
		dataToEmail.generateDataToEmail("ferreteriaselmar/");
		
		Correo sendMail = new Correo();
		sendMail.enviarCorreo();
	}

}
