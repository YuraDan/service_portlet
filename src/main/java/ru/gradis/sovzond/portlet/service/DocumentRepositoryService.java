package ru.gradis.sovzond.portlet.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by donchenko-y on 12.09.16.
 */

@Service
public interface DocumentRepositoryService {

	public String saveFile(File file, String fileName, long userId, long groupId, long folderId, String param) throws SystemException, PortalException;

	public String getfileUrlByTitle(long groupId, long folderId, String title);

	public String getfileUrlById(long fileEntryId);

	public void deleteFile(long fileEntryId, String Param) throws SystemException, PortalException;

	public void deleteFileByUuid(String uuid, long groupId);


}
