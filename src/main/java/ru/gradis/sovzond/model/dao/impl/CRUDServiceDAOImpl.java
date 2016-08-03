package ru.gradis.sovzond.model.dao.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by donchenko-y on 7/13/16.
 */

public class CRUDServiceDAOImpl implements CRUDServiceDAO {
	private static final Log log = LogFactoryUtil.getLog(CRUDServiceDAOImpl.class);

	private DataSource dataSource;

	public CRUDServiceDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Map<String, Object> executeDataAction(Action action, String dataSetName, Integer userId, String param) {
		SimpleJdbcCall simpleJdbcCall = null;

		switch (action) {
			case INSERT:
				simpleJdbcCall = initCrudProc(dataSource, "config", "pr_insert_json_data");
				break;
			case UPDATE:
				simpleJdbcCall = initCrudProc(dataSource, "config", "pr_update_json_data");
				break;
			case DELETE:
				simpleJdbcCall = initCrudProc(dataSource, "config", "pr_delete_json_data");
				break;
			case GET:
				simpleJdbcCall = initCrudProc(dataSource, "config", "pr_get_json_data");
				break;
		}

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("i_dataset_name", dataSetName);
		inParamMap.put("i_dataset_id", null);
		inParamMap.put("i_params", param);
		inParamMap.put("i_user_id", userId);
		MapSqlParameterSource in = new MapSqlParameterSource().addValues(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		log.info(simpleJdbcCallResult);

		return simpleJdbcCallResult;

	}

	private SimpleJdbcCall initCrudProc(DataSource dataSource, String schema, String procName) {
		return new SimpleJdbcCall(dataSource).withSchemaName(schema).
				withProcedureName(procName).
				declareParameters(
						new SqlParameter("i_dataset_name", Types.CLOB),
						new SqlParameter("i_dataset_id", Types.BIGINT),
						new SqlParameter("i_user_id", Types.BIGINT),
						new SqlParameter("i_params", Types.CLOB)
				);
	}

}

