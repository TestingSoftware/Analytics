package com.ncubo.analytics.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ncubo.analitycs.configuracion.Configuracion;

public class Rest {
	
	private String empresa;
	private final String ETIQUETA_REST = "productoParaLaVenta";
	private final String EMAIL_REST = "jullyad@hotmail.com";
	
	public Rest(String empresa) {
		this.empresa = empresa;
	}
	
	private BufferedReader conectarRest(String urlRest) throws MalformedURLException, IOException
	{	
		URL url = new URL(urlRest);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) 
		{
			throw new RuntimeException(String.format("No se pudo conectar al server de Nimbus en %s o lanzó un error.", new Configuracion().productoNimbus()));
		}	

		return new BufferedReader(new InputStreamReader(conn.getInputStream()));
	}
	
	public JsonObject obtenerProducto(String producto) throws MalformedURLException, IOException
	{
		BufferedReader br = conectarRest(String.format(new Configuracion().productoNimbus(), empresa, producto));
		String output;
		StringBuilder json = new StringBuilder();
		
		while ((output = br.readLine()) != null) 
		{
			json.append(output);
		}
		
		JsonObject objetoJson = (new JsonParser()).parse(json.toString()).getAsJsonObject();//convierte el string a objeto Json
		return objetoJson.get(ETIQUETA_REST).getAsJsonObject();//sirve para que cuando se llame este método solo se tenga que hacer .get("etiqueta")
	}
	
	public JsonObject obtenerProductoMasVendido(String fechaInicio, String fechaFin) throws MalformedURLException, IOException
	{
		BufferedReader br = conectarRest(String.format(new Configuracion().productoNimbus(), fechaInicio, fechaFin, EMAIL_REST));
		String output;
		StringBuilder json = new StringBuilder();
		
		while ((output = br.readLine()) != null) 
		{
			json.append(output);
		}
		
		JsonObject objetoJson = (new JsonParser()).parse(json.toString()).getAsJsonObject();//convierte el string a objeto Json
		return obtenerProducto(objetoJson.get("producto").getAsString());//obtiene el nombre del producto mas vendido del Json y lo envia al metodo obtener producto
	}

}
