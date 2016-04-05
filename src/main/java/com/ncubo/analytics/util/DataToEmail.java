package com.ncubo.analytics.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 * Hello world!
 *
 */
public class DataToEmail 
{
    public void generateDataToEmail() throws Exception
    {
       VelocityEngine filler = new VelocityEngine();
       filler.init();
       
       List<Map<String, String>> list = new ArrayList<Map<String, String>>();
       Map<String, String> map = new HashMap<String, String>();
       
       AnalyticsDatos analitycsData = new AnalyticsDatos("pagepath=~^/UI/newbikes.*");
       list = analitycsData.obtenerSesionesUsuarios();
       
       VelocityContext context = new VelocityContext();
       context.put("empresas", list);
       
       Template templateCargada = filler.getTemplate("src/main/resources/template_html.vm");
       StringWriter writer = new StringWriter();
       templateCargada.merge(context, writer);
       
       File archivo = new File("src/main/resources/generated-template.html");
       PrintWriter print = new PrintWriter(archivo);
       
       print.print(writer.toString());
       print.close();
    }
    
}
