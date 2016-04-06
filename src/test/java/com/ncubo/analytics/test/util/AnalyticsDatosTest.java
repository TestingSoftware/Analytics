package com.ncubo.analytics.test.util;

import org.testng.annotations.Test;
import com.ncubo.analytics.util.AnalyticsDatos;

public class AnalyticsDatosTest {

	private AnalyticsDatos analytics;
	
	@Test
	public void analytics() throws Exception
	{
		analytics = new AnalyticsDatos("newBikes/");
		analytics.obtenerSesionesUsuarios();
	}
	
	@Test
	public void analyticsDatos() throws Exception
	{
		analytics = new AnalyticsDatos("newBikes/");
		System.out.println(analytics.obtenerDatosPrincipales());
	}
}
