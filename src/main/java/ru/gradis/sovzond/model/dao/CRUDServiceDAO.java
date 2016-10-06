package ru.gradis.sovzond.model.dao;

import org.springframework.dao.DataAccessException;

import java.util.Map;

/**
 * Created by donchenko-y on 7/13/16.
 */


public interface CRUDServiceDAO {

	public enum Action {GET, INSERT, UPDATE, DELETE, PUBLIC_GET}

	public Map<String, Object> executeDataAction(Action action, String param) throws DataAccessException;
	
}
