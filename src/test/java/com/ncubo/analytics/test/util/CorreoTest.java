package com.ncubo.analytics.test.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import org.testng.annotations.Test;
import com.ncubo.analytics.util.Correo;

public class CorreoTest {

	private Correo correo = new Correo();
	
	@Test
	public void correo() throws AddressException, MessagingException, IOException
	{
		correo.enviarCorreo();

	}
}
