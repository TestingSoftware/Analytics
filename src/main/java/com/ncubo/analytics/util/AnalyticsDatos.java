package com.ncubo.analytics.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;
import com.google.api.services.analytics.model.GaData;


/**
 * Esta página sirve para poder realizar consultas de datos y es con la misma estructura que se sigue en esta clase
 * Por tanto, se puede realizar consultas por la página y ya una vez hechas es más fácil para poder realizarlas en estos métodos
 * https://ga-dev-tools.appspot.com/query-explorer/
 */

public class AnalyticsDatos {
	
	/**
	 * Be sure to specify the name of your application. If the application name is {@code null} or
	 * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
	 */
	private final String APPLICATION_NAME = "My Project";
	/**
	 * Ve a la página que sirve de ayuda que está al inicio, selecciona la vista y en el campo "ids"
	 * sale el id de la tabla, es el número que está después de "ga:"
	 */
	private final String TABLE_ID = "115393683";
	private String empresaFiltrar;

	/** Directory to store user credentials. */
	private final java.io.File DATA_STORE_DIR =
			new java.io.File(System.getProperty("user.home"), ".store/analytics_sample");

	private FileDataStoreFactory dataStoreFactory;
	private HttpTransport httpTransport;
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private Analytics analytics;
	
	/**
	 * This first initializes an analytics service object. It then uses the Google
	 * Analytics Management API to get the first profile ID for the authorized user. It then uses the
	 * Core Reporting API to retrieve the top 25 organic search terms. Finally the results are printed
	 * to the screen.
	 * @throws Exception 
	 */
	public AnalyticsDatos(String empresaFiltrar) throws Exception
	{
		//this.empresaFiltrar = empresaFiltrar;
		this.empresaFiltrar = "newbikes/";
		
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		analytics = initializeAnalytics();
	}

	/**
	 * Tipo business, //TODO esto no debe ir aquí o está mal
	 * @throws IOException 
	 */
	public List<Map<String, String>> obtenerSesionesUsuarios() throws IOException
	{	
		GaData gaData = obtenerSesionesUsuarios(analytics);
		return printGaData(gaData);
	}

	/** Authorizes the installed application to access user's protected data. */
	private Credential authorize() throws Exception {
		// load client secrets
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY, new InputStreamReader(
						AnalyticsDatos.class.getResourceAsStream("/client_secrets.json")));
		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, JSON_FACTORY, clientSecrets,
				Collections.singleton(AnalyticsScopes.ANALYTICS_READONLY)).setDataStoreFactory(
						dataStoreFactory).build();
		// authorize
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}

	/**
	 * Performs all necessary setup steps for running requests against the API.
	 *
	 * @return An initialized Analytics service object.
	 *
	 * @throws Exception if an issue occurs with OAuth2Native authorize.
	 */
	private Analytics initializeAnalytics() throws Exception {
		// Authorization.
		Credential credential = authorize();
		// Set up and return Google Analytics API client.
		return new Analytics.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
				APPLICATION_NAME).build();
	}

	/**
	 * Consulta a realizar
	 *
	 * @param analytics the analytics service object used to access the API.
	 * @return the response from the API.
	 * @throws IOException tf an API error occured.
	 */
	private GaData obtenerSesionesUsuarios(Analytics analytics) throws IOException {
		return analytics.data().ga().get("ga:" + TABLE_ID, // Table Id. ga: + profile id.
				"2015-01-01", // Start date.
				"2016-03-28", // End date.
				"ga:users, ga:sessions") // Metrics.
				.setDimensions("ga:pagePath")
				.setFilters("ga:pagepath=~^/UI/"+empresaFiltrar+".*")
				.execute();
	}

	/**
	 * Prints the output from the Core Reporting API. The profile name is printed along with each
	 * column name and all the data in the rows.
	 *
	 * @param results data returned from the Core Reporting API.
	 */
	private List<Map<String, String>> printGaData(GaData results) 
	{
		List<Map<String, String>> listValAn = new ArrayList<Map<String, String>>();
		Map<String, String> datos = null;
		
		if(results.getRows() == null || results.getRows().isEmpty())
		{
			return listValAn;
		}
		else
		{
			for(List<String> fila : results.getRows())
			{
				datos = new HashMap<String, String>();
				for(int i = 0; i < 3; i++)
				{
					datos.put("col" + i, fila.get(i));
				}
				listValAn.add(datos);
			}
		}
		return listValAn;
	}

}
