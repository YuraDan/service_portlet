package ru.gradis.sovzond.model.dao.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ru.gradis.sovzond.model.dao.ReportDAO;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by donchenko-y on 29.08.16.
 */

public class ReportDAOImpl implements ReportDAO {

	private static final Log log = LogFactoryUtil.getLog(ReportDAOImpl.class);

	private SimpleJdbcCall simpleJdbcCall;

	public ReportDAOImpl(DataSource dataSource) {

		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("config").
				withProcedureName("pr_get_report_filename").
				declareParameters(
						new SqlParameter("i_report_id", Types.BIGINT)
				);
	}

	@Override
	public Map<String, Object> getReportNameById(Integer reportId) {
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("i_report_id", reportId);
		MapSqlParameterSource in = new MapSqlParameterSource().addValues(inParamMap);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
//		String result = simpleJdbcCallResult.get("filename").toString();
		return simpleJdbcCallResult;
	}
}
