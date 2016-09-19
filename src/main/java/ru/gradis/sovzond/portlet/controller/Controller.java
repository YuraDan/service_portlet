package ru.gradis.sovzond.portlet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;

import javax.servlet.http.HttpSession;

/**
 * Created by donchenko-y on 15.09.16.
 */

public abstract class Controller {

	ResponseEntity<String> stringResponseEntity;

	ResponseEntity<String> verifyUserLogon(HttpSession httpSession) {
		if (httpSession.getAttribute("USER_UUID") == null ? true : false) {
			return new ResponseEntity<String>("Access is denied. The user is logged out!", HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>("login", HttpStatus.OK);
	}

	void verifyUserLogon2(HttpSession httpSession) {
		if (httpSession.getAttribute("USER_UUID") == null ? true : false) {
			stringResponseEntity = new ResponseEntity<String>("Access is denied. The user is logged out!", HttpStatus.SERVICE_UNAVAILABLE);
		} else stringResponseEntity = new ResponseEntity<String>("login", HttpStatus.OK);
	}


}
