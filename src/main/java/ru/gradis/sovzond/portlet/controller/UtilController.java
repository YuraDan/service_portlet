package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.service.UserLocalServiceUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by donchenko-y on 8/11/16.
 */

@RestController
public class UtilController {

	private static final Log log = LogFactoryUtil.getLog(UtilController.class);

	@RequestMapping(value = "/Services/getPortraitUrl", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public
	@ResponseBody
	ResponseEntity<String> getConfig(@RequestParam("userId") Integer userId, @RequestParam("imagePath") String imagePath) {
		String url = "UNDEF";
		try {
			User user = UserLocalServiceUtil.getUserById(userId);
			url = UserConstants.getPortraitURL(imagePath, user.getMale(), user.getPortraitId());
		} catch (PortalException | SystemException e) {
			log.error(e);
		}
		return new ResponseEntity<String>(url, HttpStatus.OK);
	}
}
