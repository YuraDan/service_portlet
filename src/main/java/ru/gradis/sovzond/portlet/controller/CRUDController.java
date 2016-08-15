package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;
import ru.gradis.sovzond.util.CommonUtil;

import java.util.Map;

/**
 * Created by donchenko-y on 7/13/16.
 */

@RestController
public class CRUDController {


	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private CRUDServiceDAO crudServiceDAO;


	private static final Log log = LogFactoryUtil.getLog(ConfigController.class);


	@RequestMapping(value = "/Services/getData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public
	@ResponseBody
	ResponseEntity<String> getData(@RequestParam(value = "param", required = true) String param) {
		String json = "";

		if (param != null) {
			json = (String) crudServiceDAO.executeDataAction(CRUDServiceDAO.Action.GET, param).get("r_json").toString();
			return new ResponseEntity<String>(json, HttpStatus.OK);
		}

		return new ResponseEntity<String>("Требуется передать param-json!", HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/Services/deleteData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public
	@ResponseBody
	ResponseEntity<String> delete(@RequestParam(value = "param", required = true) String param) {

		if (param != null) {
			crudServiceDAO.executeDataAction(CRUDServiceDAO.Action.DELETE, param);
			return new ResponseEntity<String>("{\"message\":\"Удалено\"", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Требуется передать param-json!", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/Services/updateData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public
	@ResponseBody
	ResponseEntity<String> update(@RequestParam(value = "param", required = true) String param) {

		if (param != null) {
			crudServiceDAO.executeDataAction(CRUDServiceDAO.Action.UPDATE, param);
			return new ResponseEntity<String>("{\"message\":\"Обновлено\"}", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Требуется передать param-json!", HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/Services/insertData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public
	@ResponseBody
	ResponseEntity<String> insert(@RequestParam(value = "param", required = true) String param) {
		String json = "";

		if (param != null) {
			Map<String, Object> result = crudServiceDAO.executeDataAction(CRUDServiceDAO.Action.INSERT, param);
			if (result.get("guid") != null) {
				json = CommonUtil.concatStrings("{", "\"id\":", result.get("id").toString(), ",", "\"guid\":\"", result.get("guid").toString(), "\"", "}");
			} else {
				json = CommonUtil.concatStrings("{", "\"id\":", result.get("id").toString(), "}");
			}
			System.out.println("json = " + json);
			return new ResponseEntity<String>(json, HttpStatus.OK);
		}

		return new ResponseEntity<String>("Требуется передать param-json!", HttpStatus.BAD_REQUEST);

	}

}
