package com.ncubo.analytics.test.util;

import org.testng.annotations.Test;
import com.ncubo.analytics.util.AnalyticsDatos;

public class AnalyticsDatosTest {

	private AnalyticsDatos analytics;
	
	@Test
	public void analytics() throws Exception
	{
		analytics = new AnalyticsDatos("newBikes/");
		System.out.println(analytics.obtenerSesionesUsuarios());
		
	}
	
	@Test
	public void analyticsDatos() throws Exception
	{
		analytics = new AnalyticsDatos("newBikes/");
		System.out.println(analytics.obtenerDatosPrincipales());
	}
	
	@Test
	public void productoMasVendido() throws Exception
	{
		analytics = new AnalyticsDatos("newBikes/");
		System.out.println(analytics.productoMasVisitado());
	}
	
	@Test
	public void nuevasVisitasYTotales() throws Exception
	{
		analytics = new AnalyticsDatos("ferreteriaselmar/");
		System.out.println(analytics.nuevasVisitasYTotales());
	}
	
	@Test
	public void visitasTotales() throws Exception
	{
		analytics = new AnalyticsDatos("ferreteriaselmar/");
		System.out.println(analytics.visitasTotales());
	}
}
