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
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;
import ru.gradis.sovzond.portlet.service.DocumentRepositoryService;
import ru.gradis.sovzond.util.JsonBuilder;

import java.io.File;

/**
 * Created by donchenko-y on 12.09.16.
 */

public class DocumentRepositoryServiceImpl implements DocumentRepositoryService {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private CRUDServiceDAO crudServiceDAO;


	@Override
	public String saveFile(File file, String fileName, long userId, long groupId, long folderId, String param) throws SystemException, PortalException {

		long repositoryId = 0;
		repositoryId = groupId;
		String fileUrl = "UNDEF";

		String mimeType = MimeTypesUtil.getContentType(file);
		String title = file.getName();

		fileName = fileName != null ? fileName : file.getName();

		//save file to repository
		FileEntry addedFile = DLAppLocalServiceUtil.addFileEntry(userId, repositoryId, folderId, fileName, mimeType,
				title, "description", "changeLog", file, getServiceContext()
		);

		fileUrl = getfileUrl(groupId, folderId, title);

		JSONObject jsonParam = JsonBuilder.getJsonFromString(param);
		jsonParam.put("_config_dataset", "dsPage");
		jsonParam.put("url", fileUrl);

		System.out.println("OUTjsonParam = " + jsonParam);

		//add information about file to database
		crudServiceDAO.executeDataAction(CRUDServiceDAO.Action.INSERT, jsonParam.toString());

		return fileUrl;
	}

	@Override
	public String getfileUrl(long groupId, long folderId, String title) {
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

	private ServiceContext getServiceContext() {
		ServiceContext serviceContext = new ServiceContext();
		String[] groupPermissions = new String[]{"VIEW", "UPDATE", "DELETE"};
		serviceContext.setGroupPermissions(groupPermissions);
		return serviceContext;
	}

}
