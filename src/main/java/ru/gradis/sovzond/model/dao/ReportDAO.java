package ru.gradis.sovzond.model.dao;

import java.util.Map;

/**
 * Created by donchenko-y on 29.08.16.
 */
public interface ReportDAO {
	public Map<String, Object> getReportNameById(Integer reportId);
}
