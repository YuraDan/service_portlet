package ru.gradis.sovzond.util;

/**
 * Created by donchenko-y on 7/6/16.
 */

public class ErrorToResponse {

	private static String errorHtml = "<!DOCTYPE html><html><head></head><body>{0}</body></html>";

	//--- Сообщение об ошибке в формате JSON ---
	public static String getJsonError(String message) {
		return String.join("", "{\"message\":\"", message.replace("\"", "\\\""), "\"}");
//		return "{\"message\":\"" + message.replace("\"", "\\\"") + "\"}";
	}

	public static String getJsonSqlError(String message, String code) {
		return String.join("", "{\"error\":\"", message.replace("\"", "\\\""), "\", \"code\":\"", code, "\"}");
	}

	//--- Сообщение об ошибке в формате HTML ---
	public static String getHtmlError(String message) {
		return String.format(errorHtml, message);
	}

}
