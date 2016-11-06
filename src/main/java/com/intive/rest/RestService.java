package com.intive.rest;

import com.intive.rest.ChartValues;

import java.util.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.FileNotFoundException;
import java.io.InputStream;

@Path("/get")
public class RestService {

	@GET
	@Path("/chartsjson")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object[]> getChartsJson() {
		ChartValues values = new ChartValues();
		generateRandomValues(values.getObjVals());

		return values.getObjVals();
	}

	@GET
	@Path("/chartshtml")
	@Produces(MediaType.TEXT_HTML)
	public InputStream getChartsHtml() throws FileNotFoundException {
		return getClass().getResourceAsStream("/templates/chartsFromJson.html");
	}

	private static void generateRandomValues(List<Object[]> vals) {
		for (int i=0; i<getRandomNumberInRange(); i++) {
			Object[] values = new Object[2];
			values[0] = "Mushrooms_" + i;
			values[1] = getRandomNumberInRange();
			vals.add(values);
		}
	}

	private static int getRandomNumberInRange() {
		Random r = new Random();
		return r.nextInt((20 - 0) + 1) + 0;
	}
}