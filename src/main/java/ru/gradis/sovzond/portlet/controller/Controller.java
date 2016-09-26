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

	private ResponseEntity verifyUserLogon(HttpSession httpSession) {
		System.out.println("**GET_USER_PRIXY_UUID**: " + httpSession.getAttribute("USER_UUID"));
		if (httpSession.getAttribute("USER_UUID") == null ? true : false) {
			return new ResponseEntity<String>("Access is denied. The user is logged out!", HttpStatus.SERVICE_UNAVAILABLE);
		}
		return new ResponseEntity<String>("login", HttpStatus.OK);
	}

	private void verifyUserLogon2(HttpSession httpSession) {
		System.out.println("**GET_USER_PRIXY_UUID**: " + httpSession.getAttribute("USER_UUID"));
		if (httpSession.getAttribute("USER_UUID") == null ? true : false) {
			stringResponseEntity = new ResponseEntity<String>("Access is denied. The user is logged out!", HttpStatus.SERVICE_UNAVAILABLE);
		} else stringResponseEntity = new ResponseEntity<String>("login", HttpStatus.OK);
	}

	public <T> ResponseEntity getResponse(HttpSession httpSession, ParamMap paramMap) {
		T answer = null;
		ResponseEntity responseEntity = verifyUserLogon(httpSession);
//		verifyUserLogon2(httpSession);
		if (responseEntity.getStatusCode() != HttpStatus.OK) return responseEntity;

		answer = process(paramMap);
		answer = answer == null ? (T) "Nothing to response!" : answer;
		if (ResponseEntity.class.isInstance(answer)) {
			return (ResponseEntity) answer;
		}
		return new ResponseEntity<String>((String) answer, HttpStatus.OK);
	}

	protected abstract <T> T process(ParamMap params);

}
