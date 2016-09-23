package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ru.gradis.sovzond.util.ParamMap;

import javax.servlet.http.HttpSession;


/**
 * Created by donchenko-y on 15.09.16.
 */

public abstract class Controller {

	private static final Log log = LogFactoryUtil.getLog(Controller.class);

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

	public <T> ResponseEntity getResponse(HttpSession httpSession, ParamMap paramMap) {
		T answer = null;
		verifyUserLogon2(httpSession);
		if (stringResponseEntity.getStatusCode() != HttpStatus.OK) {
			return stringResponseEntity;
		}
		answer = process(paramMap);
		answer = answer == null ? (T) "Nothing to response!" : answer;
		if (ResponseEntity.class.isInstance(answer)) {
			return (ResponseEntity) answer;
		}
		return new ResponseEntity<String>((String) answer, HttpStatus.OK);
	}

	protected abstract <T> T process(ParamMap params);

}
