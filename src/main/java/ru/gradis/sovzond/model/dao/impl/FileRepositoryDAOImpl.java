package ru.gradis.sovzond.model.dao.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import ru.gradis.sovzond.model.dao.FileRepositoryDAO;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by donchenko-y on 21.09.16.
 */
public class FileRepositoryDAOImpl implements FileRepositoryDAO {

	private static final Log log = LogFactoryUtil.getLog(FileRepositoryDAOImpl.class);

	private SimpleJdbcCall simpleJdbcCall;

	public FileRepositoryDAOImpl(DataSource dataSource) {
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("pages").
				withProcedureName("pr_update_body_page").
				declareParameters(
						new SqlParameter("i_body", Types.BINARY),
						new SqlParameter("i_params", Types.CLOB)
				);
	}

	@Override
	public String databaseXmlParsing(byte[] file, String param) {
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("i_body", file);
		inParamMap.put("i_params", param);
		MapSqlParameterSource in = new MapSqlParameterSource().addValues(inParamMap);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		String result = simpleJdbcCallResult.get("r_id").toString();
		return result;
	}
}
