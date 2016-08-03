package ru.gradis.sovzond.model.dao;

/**
 * Created by donchenko-y on 7/13/16.
 */


public interface ConfigDAO {

	public String getConfig(String portletID, Integer userId, Integer plId);

}
