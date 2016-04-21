package com.ncubo.analytics.util;

public class Main {

	public static void main(String[] args) throws Exception{
		DataToEmail dataToEmail = new DataToEmail();
		dataToEmail.generateDataToEmail("newbikes/", "2016/03/21", "2016/04/21");
		
		Correo sendMail = new Correo();
		sendMail.enviarCorreo();
	}

}
