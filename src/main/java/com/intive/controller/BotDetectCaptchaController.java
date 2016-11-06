package com.intive.controller;

import com.captcha.botdetect.web.servlet.Captcha;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class BotDetectCaptchaController extends BaseController {

	private static final String VIEW_BOT_DETECT = "botDetectCaptcha";

	@RequestMapping(value = "/botDetectCaptcha")
	public ModelAndView botDetectCaptcha(@RequestParam(value = "type", required = false) String type, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {
		ModelAndView model = new ModelAndView(VIEW_BOT_DETECT);
		model.addObject("captchaData", loadCaptchaData(request, response, GENERATE_HTML_URL));

		return model;
	}
	
	protected boolean validateCaptcha(HttpServletRequest request, String captchaCode, String captchaId) {
		return Captcha.load(request, "captchaData").validate(captchaCode);
	}
}