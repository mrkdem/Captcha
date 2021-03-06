package com.intive.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Service;

/**
 * Created by marek.demianiuk on 28.10.2016.
 */

@Service
public class DataService {

    private static String REST_CHARTS_JSON_URL = "http://localhost:8080/captcha/rest/get/chartsjson";
    private static String REST_CHARTS_HTML_URL = "http://localhost:8080/captcha/rest/get/chartshtml";
    private static String JSON_MEDIA_TYPE = "application/json";
	private static String HTML_MEDIA_TYPE = "text/html";

    public String getChartsHtml() {
        return getRestData(REST_CHARTS_HTML_URL, HTML_MEDIA_TYPE);
    }

    public String getChartsJson() {
        return getRestData(REST_CHARTS_JSON_URL, JSON_MEDIA_TYPE);
    }

    private String getRestData(String url, String mediaType) {
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            ClientResponse response = webResource.accept(mediaType).get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            String output = response.getEntity(String.class);

//            System.out.println("Output from Server .... \n");
//            System.out.println(output);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
