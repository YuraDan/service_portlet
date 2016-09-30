package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;
import ru.gradis.sovzond.util.CommonUtil;
import ru.gradis.sovzond.util.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by donchenko-y on 7/13/16.
 */

@RestController
public class CRUDController extends Controller {


	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private CRUDServiceDAO crudServiceDAO;


	private static final Log log = LogFactoryUtil.getLog(CRUDController.class);


	@RequestMapping(value = "/Services/getPublicData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getPublicData(@RequestParam(value = "param", required = true) String param) {
		String answer = null;
		if (param == null) return CommonUtil.getBadResponseFromString("Требуется передать param-json!");
		answer = crudServiceDAO.executeDataAction(CRUDServiceDAO.Action.PUBLIC_GET, param).get("r_json").toString();
		return CommonUtil.getOkResponseFromString(answer);
	}

	@RequestMapping(value = "/Services/getData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getData(@RequestParam(value = "param", required = true) String param, HttpServletRequest request, HttpSession httpSession) {
		return execute(param, request, httpSession, CRUDServiceDAO.Action.GET);
	}

	@RequestMapping(value = "/Services/deleteData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> delete(@RequestParam(value = "param", required = true) String param, HttpServletRequest request, HttpSession httpSession) {
		return execute(param, request, httpSession, CRUDServiceDAO.Action.DELETE);
	}

	@RequestMapping(value = "/Services/updateData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> update(@RequestParam(value = "param", required = true) String param, HttpServletRequest request, HttpSession httpSession) {
		return execute(param, request, httpSession, CRUDServiceDAO.Action.UPDATE);

	}

	@RequestMapping(value = "/Services/insertData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> insert(@RequestParam(value = "param", required = true) String param, HttpServletRequest request, HttpSession httpSession) {
		return execute(param, request, httpSession, CRUDServiceDAO.Action.INSERT);
	}

	private ResponseEntity<String> execute(String param, HttpServletRequest request, HttpSession httpSession, CRUDServiceDAO.Action action) {
		ParamMap params = new ParamMap();
		params.putString("param", param);
		params.put("action", CRUDServiceDAO.Action.class, action);
		return getResponse(request, httpSession, params);
	}

	@Override
	protected <T> T process(ParamMap params) {
		if (params.getString("param") != null) {
			return (T) crudServiceDAO.executeDataAction(params.get("action", CRUDServiceDAO.Action.class), params.getString("param")).get("r_json").toString();
		}
		return (T) CommonUtil.getBadResponseFromString("Требуется передать param-json!");
	}
}
