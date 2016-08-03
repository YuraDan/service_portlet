package ru.gradis.sovzond.model.dao;

import java.util.Map;

/**
 * Created by donchenko-y on 7/13/16.
 */


public interface CRUDServiceDAO {

	public enum Action {GET, INSERT, UPDATE, DELETE}

	public Map<String, Object> executeDataAction(Action action, String dataSetName, Integer userId, String param);


}
