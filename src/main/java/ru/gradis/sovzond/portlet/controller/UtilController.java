package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.service.UserLocalServiceUtil;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.gradis.sovzond.util.ParamMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Created by donchenko-y on 8/11/16.
 */


@RestController
public class UtilController extends Controller {

	private static final Log log = LogFactoryUtil.getLog(UtilController.class);

	@RequestMapping(value = "/Services/getPortraitUrl", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getPortraitUrl(@RequestParam("userId") long userId, @RequestParam("imagePath") String imagePath, HttpServletRequest request, HttpSession httpSession) {
		ParamMap params = new ParamMap();
		params.putString("image", "false");
		params.putLong("userId", userId);
		params.putString("imagePath", imagePath);

		return getResponse(request, httpSession, params);
	}

	@RequestMapping(value = "/Services/getPortraitImage", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity getPortraitImage(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "imagePath", required = false) String imagePath, HttpSession httpSession, HttpServletRequest httpRequest) {
		String defaultUrlString = String.join("", "http://", httpRequest.getLocalAddr(), ":", String.valueOf(httpRequest.getLocalPort()), "/", "image/user_male_portrait?img_id=0");
		String urlString;
		try {
			if (userId == null || imagePath == null) {
				urlString = defaultUrlString;
			} else {
				User user = null;
				try {
					user = UserLocalServiceUtil.getUserById(userId);
					urlString = user.isDefaultUser() || user == null ? defaultUrlString : String.join("", "http://", httpRequest.getLocalAddr(), ":", String.valueOf(httpRequest.getLocalPort()), "/", UserConstants.getPortraitURL(imagePath, user.getMale(), user.getPortraitId()));
				} catch (PortalException e) {
					urlString = defaultUrlString;
				}
			}
			URL url = new URL(urlString);
			InputStream is = url.openStream();
			BufferedImage img = ImageIO.read(is);
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", bao);
			return new ResponseEntity<byte[]>(bao.toByteArray(), HttpStatus.OK);
		} catch (SystemException | IOException e) {
			log.error(e);
		}
		return new ResponseEntity<String>("Image not found or params incorrect!", HttpStatus.BAD_REQUEST);
	}

	@Override
	protected <T> T process(ParamMap params) {
		User user = null;
		try {
			user = UserLocalServiceUtil.getUserById(params.getLong("userId"));
			return (T) UserConstants.getPortraitURL(params.getString("imagePath"), user.getMale(), user.getPortraitId());
		} catch (PortalException | SystemException e) {
			log.error(e);
		}
		return null;
	}

}
