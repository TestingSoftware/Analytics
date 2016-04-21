package com.ncubo.analytics.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ncubo.analitycs.configuracion.Configuracion;

/**
 * Hello world!
 *
 */
public class DataToEmail 
{
	
	/**
	 * Metodo que se encarga de llenar la plantilla de estadísticas que se enviará al cliente
	 * mediante el correo electrónico. 
	 * 
	 * @param empresaPorFiltrar es la empresa de la cual se desea obtener información.
	 * 
	 * */
	public void generateDataToEmail(String empresaPorFiltrar, String fechaInicio, String fechaFin) throws Exception
	{
		
		VelocityEngine filler = new VelocityEngine();
		filler.init();
		
		List<Map<String, String>> listaUsuarisoPais = new ArrayList<Map<String, String>>();
		AnalyticsDatos analitycsData = new AnalyticsDatos(empresaPorFiltrar);
	
		String productoMasVendido = analitycsData.productoMasVisitado();
		listaUsuarisoPais = analitycsData.nuevasVisitasYTotales();
		VelocityContext context = new VelocityContext();
		//TODO Cambiar el nombre de la empresa por una que esté en Prod, Cambiar el ID producto.
		Rest rest = new Rest(empresaPorFiltrar);
		
		//Construccion para mostrar o no el producto mas visto
		JsonObject jsonObject = null;
		String image = "";
		if( !productoMasVendido.equals("") )
		{
			jsonObject = rest.obtenerProducto(productoMasVendido);
			image = jsonObject.get("UrlImageneMayorPrioridad").toString().replace('"', ' ').trim();
			image = String.format(new Configuracion().getImagenNimbus(), "RepoQA",image);
		}
		//fin construccion del producto mas visto
		
		context.put("visitasTotales", analitycsData.visitasTotales());
		context.put("imagenProdMasVis", image);
		context.put("nombreDetallado", jsonObject != null ? ( jsonObject.get("NombreDetallado").toString().replace('"', ' ') ) : "");
		context.put("descripcion", jsonObject != null ? ( jsonObject.get("Descripcion").toString().replace('"', ' ') ) : "");
		
		context.put("productoMasVendidoImagen", "");
		context.put("productoMasVendidoNombre", rest.obtenerProductoMasVendido(fechaInicio, fechaFin));
		context.put("productoMasVendidoDescripcion", "");
		context.put("usuariosPais", listaUsuarisoPais);
	
	
		Template templateCargada = filler.getTemplate(new Configuracion().getTEMPLATE_URL());
		StringWriter writer = new StringWriter();
		templateCargada.merge(context, writer);
		
		File archivo = new File(new Configuracion().getGENERATED_TEMPLATE());
		PrintWriter print = new PrintWriter(archivo);
		
		print.print(writer.toString());
		print.close();
    }
}
