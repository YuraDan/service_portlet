package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gradis.sovzond.model.dao.ConfigDAO;
import ru.gradis.sovzond.util.CommonUtil;
import ru.gradis.sovzond.util.ParamMap;

import javax.servlet.http.HttpSession;

/**
 * Created by donchenko-y on 7/13/16.
 */

@RestController
public class ConfigController extends Controller {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private ConfigDAO configDAO;

	private static final Log log = LogFactoryUtil.getLog(ConfigController.class);

	@RequestMapping(value = "/Services/getConfig", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getConfig(@RequestParam("param") String param, HttpSession httpSession) {
		ParamMap params = new ParamMap();
		params.putString("param", param);
		return getResponse(httpSession, params);
	}


	@Override
	protected <T> T process(ParamMap params) {
		String json = "";
		if (params.getString("param") == null) {
			return (T) CommonUtil.getBadResponseFromString("Заданы не все параметры!");
		} else {
			json = (String) configDAO.getConfig(params.getString("param"));
			return (T) json;
		}

	}
}
