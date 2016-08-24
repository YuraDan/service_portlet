package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gradis.sovzond.util.JsonBuilder;

import java.util.Map;


/**
 * Created by donchenko-y on 8/16/16.
 */

@Controller
public class ReportController {
	private static final Log log = LogFactoryUtil.getLog(ReportController.class);


	@RequestMapping(value = "/Services/showReport", method = RequestMethod.POST)
	public String showReport(@RequestParam(value = "param", required = false) String param, RedirectAttributes redirectAttributes) {
		String name = "UNDEFINED";
		Map<String, Object> map = null;
		try {
			name = JsonBuilder.getValueFromJsonString(param, "_config_filename");
		} catch (JSONException e) {
			log.error(e);
		} finally {
			redirectAttributes.addAttribute("name", name);
			redirectAttributes.addAttribute("param", param);
			return "redirect:/" + getReportUrl(true);
		}
	}

	@RequestMapping(value = "/Services/showDesigner", method = RequestMethod.POST)
	public String showDesigner(@RequestParam(value = "param", required = false) String param, RedirectAttributes redirectAttributes) {
		String name = "UNDEFINED";
		Map<String, Object> map = null;
		try {
			name = JsonBuilder.getValueFromJsonString(param, "_config_filename");
		} catch (JSONException e) {
			log.error(e);
		} finally {
			redirectAttributes.addAttribute("name", name);
			redirectAttributes.addAttribute("param", param);
			return "redirect:/" + getReportUrl(false);
		}
	}

	private String getUrl(String host, String port, boolean view) {
		String rep;
		rep = (view == true) ? "/Report_Viewer" : "/Report_Designer";
		StringBuilder sb = new StringBuilder();
		sb.append(host).append(":").append(port).append(rep);
		return sb.toString();
	}

	private String getReportUrl(boolean view) {
		String rep;
		rep = (view == true) ? "Report_Viewer" : "Report_Designer";
		return rep;
	}
}