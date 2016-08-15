
package ru.gradis.sovzond.portlet.controller;

import java.io.IOException;
import java.util.Locale;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {


	private static final Log log = LogFactoryUtil.getLog(HomeController.class);

	@RequestMapping("EDIT")
	public ModelAndView home(Locale locale, ModelAndView model) throws IOException {
		log.info("Welcome from SERVICE_PORTLET! The client locale is " + locale.toString());
		model.setViewName("home");
		return model;
	}


}
