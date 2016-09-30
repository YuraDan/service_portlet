package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gradis.sovzond.portlet.service.DocumentRepositoryService;

import ru.gradis.sovzond.util.ParamMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * Created by donchenko-y on 08.09.16.
 */


@RestController
public class DocumentRepositoryController extends Controller {

	private static final Log log = LogFactoryUtil.getLog(DocumentRepositoryController.class);

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private DocumentRepositoryService documentRepositoryService;

	@RequestMapping(value = "/Services/addNewFile", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> saveFile(@RequestParam("files[]") MultipartFile file, @RequestParam(value = "param", required = true) String param, @RequestParam("userid") long userId, @RequestParam("groupid") long groupId, @RequestParam("folderid") long folderId) {
		String json = "";

		if (file == null || param == null)
			return new ResponseEntity<String>("Требуется передать параметры и файлы!", HttpStatus.BAD_REQUEST);
		try {
			json = documentRepositoryService.saveFile(file, null, userId, groupId, folderId, param);
//				json = documentRepositoryService.saveFile(multipartToFile(file), null, 20434, 20182, 0);
			return new ResponseEntity<String>(json, HttpStatus.OK);
		} catch (IOException | PortalException | SystemException e) {
			log.error(e);
			return new ResponseEntity<String>(String.join("", "{", "message:", "\"", e.toString(), "\"", "}"), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/Services/addNewFolder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity addFolder(@RequestParam(value = "param", required = true) String param, @RequestParam("userid") long userId, @RequestParam("groupid") long groupId, @RequestParam("name") String name) {
		if (param == null) return new ResponseEntity<String>("Требуется передать параметры !", HttpStatus.BAD_REQUEST);
		try {
			Long folderId = documentRepositoryService.addNewFolder(userId, groupId, name);
			return new ResponseEntity<Long>(folderId, HttpStatus.OK);
		} catch (PortalException | SystemException e) {
			log.error(e);
			return new ResponseEntity<String>(String.join("", "{", "message:", "\"", e.toString(), "\"", "}"), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/Services/getFileUrl", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getFileUrl(@RequestParam(value = "param", required = true) String param, @RequestParam("groupId") long groupId, @RequestParam("folderId") long folderId, @RequestParam("title") String title, HttpServletRequest request, HttpSession httpSession) {
		ParamMap params = new ParamMap();
		params.putLong("groupId", groupId);
		params.putLong("folderId", folderId);
		params.putString("title", title);
		return getResponse(request, httpSession, params);
	}

	@RequestMapping(value = "/Services/deleteFile", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteFile(@RequestParam(value = "param", required = true) String param, @RequestParam("fileEntryId") long fileEntryId, HttpServletRequest request, HttpSession httpSession) {
		String json = "";
		ParamMap params = new ParamMap();
		params.put("fileAction", DocumentRepositoryService.FileAction.class, DocumentRepositoryService.FileAction.DELETE_FILE);
		params.putString("param", param);
		params.putLong("fileAction", fileEntryId);
		return getResponse(request, httpSession, params);
	}

	@Override
	protected <T> T process(ParamMap params) {
		if (params.getString("param") == null)
			return (T) new ResponseEntity<String>("Требуется передать параметры!", HttpStatus.BAD_REQUEST);
		switch (params.get("fileAction", DocumentRepositoryService.FileAction.class)) {
			case DELETE_FILE: {
				String json = "";
				try {
					documentRepositoryService.deleteFile(params.getLong("fileEntryID"), params.getString("param"));
				} catch (SystemException | PortalException e) {
					log.error(e);
					json = String.join("", "{", "message:", "\"", e.toString(), "\"", "}");
					return (T) new ResponseEntity<String>(json, HttpStatus.BAD_REQUEST);
				}
				return (T) new ResponseEntity<String>("Deleted", HttpStatus.OK);
			}
			case GET_FILE_URL: {
				return (T) documentRepositoryService.getfileUrlByTitle(params.getLong("groupId"), params.getLong("folderId"), params.getString("title"));
			}
		}
		return (T) new ResponseEntity<String>("Error file action in document service!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
