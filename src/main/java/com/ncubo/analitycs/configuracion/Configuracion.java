package com.ncubo.analitycs.configuracion;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

public class Configuracion 
{
	private final String TEMPLATE_URL;
	private final String GENERATED_TEMPLATE;

	public String getTEMPLATE_URL() 
	{
		return TEMPLATE_URL.toString();
	}

	public String getGENERATED_TEMPLATE()
	{
		return GENERATED_TEMPLATE.toString();
	}
	/**
	 * Este constructor se encarga de inicializar las constantes presentes en el archivo .properties
	 * y cuya principal finalidad es obtener parametros previamente definidos para la aplicación.
	 * */
	public Configuracion() throws IOException
	{
		Properties propiedades = new Properties();
		InputStream inputStream = this.getClass().getResourceAsStream("/configuracion.properties");
		
		propiedades.load(inputStream);
		this.TEMPLATE_URL = propiedades.getProperty("template_url");
		this.GENERATED_TEMPLATE = propiedades.getProperty("generated_template");
	}
}
