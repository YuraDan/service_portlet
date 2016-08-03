package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gradis.sovzond.model.dao.ConfigDAO;

/**
 * Created by donchenko-y on 7/13/16.
 */

@RestController
public class ConfigController {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private ConfigDAO configDAO;

	private static final Log log = LogFactoryUtil.getLog(ConfigController.class);

	@RequestMapping(value = "/Services/getConfig", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public
	@ResponseBody
	ResponseEntity<String> getConfig(@RequestParam("portletId") String portletId,
	                                 @RequestParam("userId") Integer userId,
	                                 @RequestParam("plId") Integer plId
	) {
		String json = "";

		if (portletId == null || userId == null || plId == null) {
			return new ResponseEntity<String>("Заданы не все параметры!", HttpStatus.BAD_REQUEST);
		} else {
			json = (String) configDAO.getConfig(portletId, userId, plId);
			return new ResponseEntity<String>(json, HttpStatus.OK);
		}

	}


}
