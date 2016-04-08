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
	public void generateDataToEmail(String empresaPorFiltrar) throws Exception
    {
       VelocityEngine filler = new VelocityEngine();
       filler.init();
       
       List<Map<String, String>> listaUsuarisoPais = new ArrayList<Map<String, String>>();
       AnalyticsDatos analitycsData = new AnalyticsDatos(empresaPorFiltrar);

       String productoMasVendido = analitycsData.productoMasVisitado();
       listaUsuarisoPais = analitycsData.nuevasVisitasYTotales();
       VelocityContext context = new VelocityContext();

       context.put("productoMasVendido", productoMasVendido);
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
