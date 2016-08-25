package ru.gradis.sovzond.model.dao.impl;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.increment.IntegerOverrideIncrement;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import ru.gradis.sovzond.model.dao.LoginDAO;
import sun.java2d.pipe.SpanShapeRenderer;


import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by donchenko-y on 8/8/16.
 */
public class LoginDAOImpl implements LoginDAO {

	private static final Log log = LogFactoryUtil.getLog(LoginDAOImpl.class);

	private SimpleJdbcCall simpleJdbcCall;

	public LoginDAOImpl(DataSource dataSource) {

		this.simpleJdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("public").
				withProcedureName("PR_INIT").
				declareParameters(
						new SqlParameter("user_id", Types.BIGINT)
				);
	}

	@Override
	public void userInit(String userId) {

		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("user_id", Integer.valueOf(userId));
		MapSqlParameterSource in = new MapSqlParameterSource().addValues(inParamMap);
		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);
		log.info(simpleJdbcCallResult);

	}

}
