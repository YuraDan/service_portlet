package ru.gradis.sovzond.model.dao.impl;

import org.springframework.dao.DataAccessException;
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

	private DataSource dataSource;

	public CRUDServiceDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Map<String, Object> executeDataAction(Action action, String param) throws DataAccessException {
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("i_params", param);
		MapSqlParameterSource in = new MapSqlParameterSource().addValues(inParamMap);
		return getJdbcCallByAction(action).execute(in);
	}

	private SimpleJdbcCall getJdbcCallByAction(Action action) {
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
			case PUBLIC_GET:
				simpleJdbcCall = initCrudProc(dataSource, "config", "pr_get_json_data_public");
				break;
		}
		return simpleJdbcCall;
	}

	private SimpleJdbcCall initCrudProc(DataSource dataSource, String schema, String procName) {
		return new SimpleJdbcCall(dataSource).withSchemaName(schema).
				withProcedureName(procName).
				declareParameters(
						new SqlParameter("i_params", Types.CLOB)
				);
	}

}

