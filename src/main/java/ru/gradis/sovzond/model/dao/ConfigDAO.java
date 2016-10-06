package ru.gradis.sovzond.model.dao;

import org.springframework.dao.DataAccessException;

/**
 * Created by donchenko-y on 7/13/16.
 */


public interface ConfigDAO {

	public String getConfig(String param) throws DataAccessException;

}
