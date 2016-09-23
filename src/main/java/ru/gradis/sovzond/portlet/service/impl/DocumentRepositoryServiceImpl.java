package ru.gradis.sovzond.portlet.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;
import ru.gradis.sovzond.model.dao.FileRepositoryDAO;
import ru.gradis.sovzond.portlet.service.DocumentRepositoryService;
import ru.gradis.sovzond.util.JsonBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Created by donchenko-y on 12.09.16.
 */

public class DocumentRepositoryServiceImpl implements DocumentRepositoryService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private CRUDServiceDAO crudServiceDAO;

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private FileRepositoryDAO fileRepositoryDAO;


	@Override
	public String saveFile(MultipartFile mFile, String fileName, long userId, long groupId, long folderId, String param) throws SystemException, PortalException, IOException {
		long repositoryId = 0;
		repositoryId = groupId;
		String fileUrl, insertResult = "UNDEF";
		byte[] bytesOfFile = mFile.getBytes();
		File file = multipartToFile(mFile);

		String mimeType = MimeTypesUtil.getContentType(file);
		String title = file.getName();
		fileName = fileName != null ? fileName : file.getName();

		//save file to repository
		FileEntry addedFile = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId, folderId, fileName, mimeType,
				title, "description", "changeLog", file, getServiceContext()
		);

		fileUrl = getfileUrlByTitle(groupId, folderId, title);
		JSONObject jsonParam = JsonBuilder.getJsonFromString(param);
		jsonParam.put("_config_dataset", "dsPage");
		jsonParam.put("url", fileUrl);
		jsonParam.put("fileentryid", addedFile.getFileEntryId());
		//add information about file to database
		insertResult = crudServiceDAO.executeDataAction(CRUDServiceDAO.Action.INSERT, jsonParam.toString()).get("r_json").toString();
		//parsing if xml extension
		if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("xml") || FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("csv")) {
			fileRepositoryDAO.databaseXmlParsing(bytesOfFile, insertResult);
		}
		return fileUrl;
	}

	@Override
	public String getfileUrlByTitle(long groupId, long folderId, String title) {
		DLFileEntry fileEntry = null;
		String url = "UNDEF";
		try {
			fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(groupId, folderId, title);
//			fileEntry = DLAppServiceUtil.getFileEntry(groupId, folderId, title);
//			url = "/documents/" + fileEntry.getGroupId() + "/" + fileEntry.getFolderId() + "/" + fileEntry.getTitle() + "/" + fileEntry.getUuid();
			url = String.join("/", "/documents", String.valueOf(fileEntry.getRepositoryId()),
					String.valueOf(fileEntry.getFolderId()), HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle()), true)
			);
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}
		return url;
	}

	@Override
	public String getfileUrlById(long fileEntryId) {
		DLFileEntry fileEntry = null;
		String url = "UNDEF";
		try {
			fileEntry = DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId);
			url = String.join("/", "/documents", String.valueOf(fileEntry.getRepositoryId()),
					String.valueOf(fileEntry.getFolderId()), HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle()), true)
			);
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}
		return url;
	}

	@Override
	public Long addNewFolder(long userId, long groupId, String name) throws SystemException, PortalException {
		DLFolder addedFolder = DLFolderLocalServiceUtil.addFolder(userId, groupId, groupId, false,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, "description", false, getServiceContext()
		);
		return addedFolder.getFolderId();
	}

	@Override
	public void deleteFile(long fileEntryId, String param) throws PortalException, SystemException {
		DLFileEntry fileEntry = null;
		DLFileEntryLocalServiceUtil.deleteFileEntry(fileEntryId);
		JSONObject jsonParam = JsonBuilder.getJsonFromString(param);
		jsonParam.put("_config_dataset", "dsPage");
		crudServiceDAO.executeDataAction(CRUDServiceDAO.Action.DELETE, jsonParam.toString());

	}

	@Override
	public void deleteFileByUuid(String uuid, long groupId) {
		DLFileEntry fileEntry = null;
		try {
			fileEntry = DLFileEntryLocalServiceUtil.getFileEntryByUuidAndGroupId(uuid, groupId);
			DLFileEntryLocalServiceUtil.deleteFileEntry(fileEntry);
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}
	}

	private ServiceContext getServiceContext() {
		ServiceContext serviceContext = new ServiceContext();
		String[] groupPermissions = new String[]{"VIEW", "UPDATE", "DELETE"};
		serviceContext.setGroupPermissions(groupPermissions);
		return serviceContext;
	}

	private java.io.File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		java.io.File convFile = new java.io.File(multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}
}
