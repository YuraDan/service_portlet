package ru.gradis.sovzond.model.domain;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by donchenko-y on 7/1/16.
 */
public class FileVO {

	private CommonsMultipartFile fileData;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}
}
