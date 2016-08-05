package ru.gradis.sovzond.model.dao.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ru.gradis.sovzond.model.dao.ConfigDAO;


import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by donchenko-y on 7/13/16.
 */

public class ConfigDAOImpl implements ConfigDAO {

	private static final Log log = LogFactoryUtil.getLog(ConfigDAOImpl.class);

	private SimpleJdbcCall simpleJdbcCall;

	public ConfigDAOImpl(DataSource dataSource) {

		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("config").
				withProcedureName("pr_get_json_portlet").
				declareParameters(
						new SqlParameter("i_layout_id", Types.BIGINT),
						new SqlParameter("i_portlet_id", Types.CLOB),
						new SqlParameter("i_user_id", Types.BIGINT)
				);
	}

	@Override
	public String getConfig(String param) {
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("i_param", param);
		MapSqlParameterSource in = new MapSqlParameterSource().addValues(inParamMap);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		log.info(simpleJdbcCallResult);
		String result = simpleJdbcCallResult.get("r_json").toString();
		return result;

	}

}
