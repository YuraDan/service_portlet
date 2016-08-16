package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gradis.sovzond.model.dao.LoginDAO;

/**
 * Created by donchenko-y on 8/8/16.
 */

@RestController
public class LoginController {


	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private LoginDAO loginDAO;

	private static final Log log = LogFactoryUtil.getLog(ConfigController.class);

	@RequestMapping(value = "/Services/loginInit", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getConfig(@RequestParam("userId") String userId) {
		if (userId == null) {
			return new ResponseEntity<String>("Заданы не все параметры!", HttpStatus.BAD_REQUEST);
		} else {
			loginDAO.userInit(userId);
			return new ResponseEntity<String>("Пользователь инициализирован!", HttpStatus.OK);
		}

	}

}
