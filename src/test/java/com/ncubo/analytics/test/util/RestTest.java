package com.ncubo.analytics.test.util;

import java.io.IOException;
import java.net.MalformedURLException;

import org.testng.annotations.Test;

import com.ncubo.analytics.util.Rest;

public class RestTest {
	
	private Rest rest = new Rest("newbikes");
	
	@Test
	public void Rest() throws MalformedURLException, IOException
	{
		rest.obtenerProducto("producto2");
	}
}
