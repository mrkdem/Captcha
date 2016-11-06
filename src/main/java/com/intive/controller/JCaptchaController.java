package com.intive.controller;

import javax.servlet.ServletOutputStream;
import com.intive.jcaptcha.CaptchaServiceSingleton;
import com.octo.captcha.service.CaptchaServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

@RestController
public class JCaptchaController extends BaseController {

	private static final String VIEW_JCAPTCHA = "jcaptcha";
	private static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

	@RequestMapping("/captcha.html")
	public void showForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		// the output stream to render the captcha image as jpeg into
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream(); 
		try {
			// get the session id that will identify the generated captcha.
			// the same id must be used to validate the response, the session id is a good candidate!
			
			String captchaId = request.getSession().getId();
			BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(captchaId, request.getLocale()); 
			
			ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND); 
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
			return;
		}
		
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray(); 
		
		// flush it in the response 
		response.setHeader("Cache-Control", "no-store"); 
		response.setHeader("Pragma", "no-cache"); 
		response.setDateHeader("Expires", 0); 
		response.setContentType("image/"+CAPTCHA_IMAGE_FORMAT); 
		
		ServletOutputStream responseOutputStream = response.getOutputStream(); 
		responseOutputStream.write(captchaChallengeAsJpeg); 
		responseOutputStream.flush(); 
		responseOutputStream.close();
	}

	@RequestMapping(value = "/jcaptcha")
	public ModelAndView jCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {
		ModelAndView model = new ModelAndView(VIEW_JCAPTCHA);
		try {
			model.addObject("captchaData", loadCaptchaData(request, response, GENERATE_PDF_URL));
		} catch (CaptchaServiceException e) {
			//should not happen, may be thrown if the id is not valid
		}

		return model;
	}

	protected boolean validateCaptcha(HttpServletRequest request, String captchaCode, String captchaId) {
		return CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, captchaCode);
	}
}