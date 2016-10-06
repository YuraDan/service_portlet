package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.gradis.sovzond.util.CommonUtil;
import ru.gradis.sovzond.util.ErrorToResponse;
import ru.gradis.sovzond.util.ParamMap;
import ru.gradis.sovzond.util.exception.DataException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import java.security.Key;
import java.sql.SQLException;

/**
 * Created by donchenko-y on 15.09.16.
 */

@ControllerAdvice
public abstract class Controller {

	private static final Log log = LogFactoryUtil.getLog(Controller.class);

	ResponseEntity<String> stringResponseEntity;

	private ResponseEntity verifyUserLogon(HttpServletRequest req, HttpSession httpSession) {
		ResponseEntity errorLogin = new ResponseEntity<String>("Access is denied. The user is logged out!", HttpStatus.SERVICE_UNAVAILABLE);
		Cookie[] cookies = req.getCookies();
		String userId = null, password = null, companyId = null, uuid = null, rememberMe = "false";
		String sessionUserId = httpSession.getAttribute("USER_ID") != null ? httpSession.getAttribute("USER_ID").toString() : "USER_UNDEFINED";
		for (Cookie c : cookies) {
			companyId = c.getName().equals("COMPANY_ID") ? c.getValue() : companyId;
			userId = c.getName().equals("ID") ? CommonUtil.hexStringToStringByAscii(c.getValue()) : userId;
			rememberMe = c.getName().equals("REMEMBER_ME") ? c.getValue() : rememberMe;
		}
//		System.out.println("sessionUserId = " + sessionUserId);
		if (userId == null || companyId == null) return errorLogin;
		if (rememberMe.equals("false") && sessionUserId == null) return errorLogin;
		try {
			Company company = CompanyLocalServiceUtil.getCompany(Long.valueOf(companyId));
			Key key = company.getKeyObj();
			String userDecrypt = Encryptor.decrypt(key, userId);
//			System.out.println("userDecrypt = " + userDecrypt);
			if (rememberMe.equals("true") && userDecrypt.length() > 1) {
				return new ResponseEntity<String>(userDecrypt, HttpStatus.OK);
			}
			if (userDecrypt.length() > 1 && userDecrypt.equals(sessionUserId)) {
				return new ResponseEntity<String>(userDecrypt, HttpStatus.OK);
			}
		} catch (NumberFormatException | PortalException | SystemException | EncryptorException e) {
			log.error(e);
		}
		return new ResponseEntity<String>("User check Error! No cookies...", HttpStatus.SERVICE_UNAVAILABLE);
	}

	public <T> ResponseEntity getResponse(HttpServletRequest request, HttpSession httpSession, ParamMap paramMap) {
		T answer = null;
		ResponseEntity responseEntity = verifyUserLogon(request, httpSession);
		if (responseEntity.getStatusCode() != HttpStatus.OK) return responseEntity;
		answer = process(paramMap);
		answer = answer == null ? (T) "Nothing to response!" : answer;
		if (ResponseEntity.class.isInstance(answer)) {
			return (ResponseEntity) answer;
		}
		return new ResponseEntity<String>((String) answer, HttpStatus.OK);
	}

	protected abstract <T> T process(ParamMap params);

	@ExceptionHandler(DataException.class)
	@ResponseBody
	public ResponseEntity<String> handleException(DataException e, HttpServletResponse response) {
		log.error(e);
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Content-Type", "application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		if (e.getRootCause() instanceof SQLException) {
			SQLException s = (SQLException) e.getRootCause();
			return CommonUtil.getErrorResponse(ErrorToResponse.getJsonSqlError(s.getMessage(), s.getSQLState()));
		} else {
			log.error(e);
			return CommonUtil.getErrorResponse(ErrorToResponse.getJsonError(e.getMessage()));
		}
	}

//TODO: Delete
//	private ResponseEntity verifyUserLogon(HttpSession httpSession) {
//		System.out.println("**GET_USER_PRIXY_UUID**: " + httpSession.getAttribute("ID"));
//		if (httpSession.getAttribute("ID") == null ? true : false) {
//			return new ResponseEntity<String>("Access is denied. The user is logged out!", HttpStatus.SERVICE_UNAVAILABLE);
//		}
//		return new ResponseEntity<String>("login", HttpStatus.OK);
//	}
//
//	private void verifyUserLogon2(HttpSession httpSession) {
//		System.out.println("**GET_USER_PRIXY_UUID**: " + httpSession.getAttribute("USER_UUID"));
//		if (httpSession.getAttribute("USER_UUID") == null ? true : false) {
//			stringResponseEntity = new ResponseEntity<String>("Access is denied. The user is logged out!", HttpStatus.SERVICE_UNAVAILABLE);
//		} else stringResponseEntity = new ResponseEntity<String>("login", HttpStatus.OK);
//	}


}
