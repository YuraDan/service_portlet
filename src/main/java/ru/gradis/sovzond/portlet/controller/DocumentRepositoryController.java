package ru.gradis.sovzond.portlet.controller;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gradis.sovzond.portlet.service.DocumentRepositoryService;
import ru.gradis.sovzond.util.CommonUtil;
import ru.gradis.sovzond.util.JsonBuilder;

import java.io.*;

/**
 * Created by donchenko-y on 08.09.16.
 */


@RestController
public class DocumentRepositoryController {

	private static final Log log = LogFactoryUtil.getLog(DocumentRepositoryController.class);

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private DocumentRepositoryService documentRepositoryService;

	@RequestMapping(value = "/Services/addNewFile", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> saveFile(@RequestParam("files[]") MultipartFile file, @RequestParam(value = "param", required = true) String param, @RequestParam("userid") long userId, @RequestParam("groupid") long groupId, @RequestParam("folderid") long folderId) {
		String json = "";

		if (file != null && param != null) {
			System.out.println("param = " + param);
			try {
				json = documentRepositoryService.saveFile(multipartToFile(file), null, userId, groupId, folderId, param);
//				json = documentRepositoryService.saveFile(multipartToFile(file), null, 20434, 20182, 0);
				return new ResponseEntity<String>(json, HttpStatus.OK);
			} catch (IOException | PortalException | SystemException e) {
				log.error(e);
				json = String.join("", "{", "message:", "\"", e.toString(), "\"", "}");
				return new ResponseEntity<String>(json, HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>("Требуется передать параметры и файлы!", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/Services/addNewFolder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> addFolder(@RequestParam(value = "param", required = true) String param) {
		String json = "";

//		if (param != null) {
//			DLFolder addedFolder = DLFolderLocalServiceUtil.addFolder(
//					userId,
//					groupId,
//					repoId, //GroupID og Site
//					false,
//					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
//					"folderName",
//					"description",
//					false,
//					new ServiceContext());
//		}

		return new ResponseEntity<String>("Требуется передать параметры !", HttpStatus.BAD_REQUEST);

	}


	@RequestMapping(value = "/Services/getFileUrl", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getFileUrl(@RequestParam(value = "param", required = true) String param, @RequestParam("groupId") long groupId, @RequestParam("folderId") long folderId, @RequestParam("title") String title) {
		String json = "";

		if (param != null) {
			json = documentRepositoryService.getfileUrl(groupId, folderId, title);
			return new ResponseEntity<String>(json, HttpStatus.OK);
		}

		return new ResponseEntity<String>("Требуется передать параметры!", HttpStatus.BAD_REQUEST);

	}

	private java.io.File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		java.io.File convFile = new java.io.File(multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}


}
