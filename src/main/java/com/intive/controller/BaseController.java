package com.intive.controller;

import com.google.gson.Gson;
import com.intive.model.CaptchaData;
import com.intive.service.DataService;
import com.intive.utils.ResourceReader;
import com.intive.utils.StreamResourceReader;
import com.intive.utils.StreamResponseExtractor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
public abstract class BaseController {

	private static final String VIEW_INDEX = "index";
	protected static final String GENERATE_PDF_URL = "http://localhost:3000/generate-pdf-from-data-and-dot-template";
	protected static final String GENERATE_HTML_URL = "http://localhost:3000/generate-html-from-data";
	private final Gson gson;
	private DataService service;

	public BaseController() {
		service = new DataService();
		gson = new Gson();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		System.out.println("index");

		return VIEW_INDEX;
	}

	protected abstract boolean validateCaptcha(HttpServletRequest request, String captchaCode, String captchaId);
	
	protected CaptchaData loadCaptchaData(HttpServletRequest request, HttpServletResponse response, String generateUrl) throws IOException, URISyntaxException {
		CaptchaData captchaData = new CaptchaData();
		captchaData.setCaptchaCode(request.getParameter("captchaCode"));

		String captchaId = request.getSession().getId();
		if (StringUtils.isNotBlank(captchaData.getCaptchaCode())) {
			boolean isCorrect = validateCaptcha(request, captchaData.getCaptchaCode(), captchaId);
			if (isCorrect) {
				captchaData.setCaptchaCorrect("Correct code");
				captchaData.setCaptchaIncorrect("");
				executeTemplateFromHtmlAndJson(response, service.getChartsHtml(), service.getChartsJson(), generateUrl);
				response.flushBuffer();
			} else {
				captchaData.setCaptchaCorrect("");
				captchaData.setCaptchaIncorrect("Incorrect code");
			}

			captchaData.setCaptchaCode("");
		}

		return captchaData;
	}

	protected void executeTemplateFromHtmlAndJson(HttpServletResponse response, String html, String data, String sUrl) throws IOException, URISyntaxException {
//		System.out.println("html = " + html);
//		System.out.println("json = " + data);

		Map model = new HashMap();
		model.put("html", html);
		model.put("data", data);
		RestTemplate restTemplate = new RestTemplate();
		URI url = new URI(sUrl);
		RequestCallback cb = clientHttpRequest -> {
			Writer nodeWriter = new OutputStreamWriter(clientHttpRequest.getBody());
			nodeWriter.write(gson.toJson(model));
			nodeWriter.flush();
			clientHttpRequest.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		};
		ResourceReader reader = new StreamResourceReader(response);
		restTemplate.execute(url, HttpMethod.POST, cb, new StreamResponseExtractor(reader));
	}
}