package ru.gradis.sovzond.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.gradis.sovzond.model.dao.ConfigDAO;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;
import ru.gradis.sovzond.model.dao.impl.ConfigDAOImpl;
import ru.gradis.sovzond.model.dao.impl.CRUDServiceDAOImpl;

import javax.sql.DataSource;

/**
 * Created by donchenko-y on 6/1/16.
 */

@Configuration
@ComponentScan(basePackages = "ru.gradis.sovzond")
@EnableWebMvc
public class RootContext extends WebMvcConfigurerAdapter {


	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://192.168.42.21:5432/mo");
		dataSource.setUsername("rc7postgres");
		dataSource.setPassword("9PmAPWXHefUn");

		return dataSource;
	}

	@Bean
	public ConfigDAO configDAO() {
		return new ConfigDAOImpl(getDataSource());
	}


	@Bean
	public CRUDServiceDAO crudServiceDAO() {
		return new CRUDServiceDAOImpl(getDataSource());
	}


}
