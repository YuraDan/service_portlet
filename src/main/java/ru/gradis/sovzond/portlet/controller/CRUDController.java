package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;
import ru.gradis.sovzond.util.CommonUtil;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by donchenko-y on 7/13/16.
 */

@RestController
public class CRUDController extends Controller {


	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private CRUDServiceDAO crudServiceDAO;


	private static final Log log = LogFactoryUtil.getLog(ConfigController.class);

	@RequestMapping(value = "/Services/getData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getData(@RequestParam(value = "param", required = true) String param, HttpSession httpSession) {
		return execute(param, httpSession, CRUDServiceDAO.Action.GET);
	}

	@RequestMapping(value = "/Services/deleteData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> delete(@RequestParam(value = "param", required = true) String param, HttpSession httpSession) {
		return execute(param, httpSession, CRUDServiceDAO.Action.DELETE);
	}

	@RequestMapping(value = "/Services/updateData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> update(@RequestParam(value = "param", required = true) String param, HttpSession httpSession) {
		return execute(param, httpSession, CRUDServiceDAO.Action.UPDATE);

	}

	@RequestMapping(value = "/Services/insertData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> insert(@RequestParam(value = "param", required = true) String param, HttpSession httpSession) {
		return execute(param, httpSession, CRUDServiceDAO.Action.INSERT);
	}

	private ResponseEntity<String> execute(String param, HttpSession httpSession, CRUDServiceDAO.Action action) {
		String json = "";

		verifyUserLogon2(httpSession);

		if (stringResponseEntity.getStatusCode() == HttpStatus.OK) {
			if (param != null) {
				json = (String) crudServiceDAO.executeDataAction(action, param).get("r_json").toString();
				return new ResponseEntity<String>(json, HttpStatus.OK);
			}

			return new ResponseEntity<String>("Требуется передать param-json!", HttpStatus.BAD_REQUEST);
		} else {
			return stringResponseEntity;
		}
	}

}
