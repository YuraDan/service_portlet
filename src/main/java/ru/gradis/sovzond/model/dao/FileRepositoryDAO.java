package ru.gradis.sovzond.model.dao;

/**
 * Created by donchenko-y on 21.09.16.
 */

public interface FileRepositoryDAO {
	String databaseXmlParsing(byte[] file, String param);
}
