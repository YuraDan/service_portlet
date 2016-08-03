package ru.gradis.sovzond.model.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;

/**
 * Created by donchenko-y on 7/1/16.
 */
public class DefaultDAO {

	private JdbcOperations jdbcTemplate;


	public DefaultDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public JdbcOperations getTemplate() {
		return jdbcTemplate;
	}


}
