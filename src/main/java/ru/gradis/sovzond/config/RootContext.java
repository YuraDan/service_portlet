package ru.gradis.sovzond.config;

import com.liferay.portal.kernel.util.InfrastructureUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ru.gradis.sovzond.model.dao.CRUDServiceDAO;
import ru.gradis.sovzond.model.dao.ConfigDAO;
import ru.gradis.sovzond.model.dao.LoginDAO;
import ru.gradis.sovzond.model.dao.ReportDAO;
import ru.gradis.sovzond.model.dao.impl.CRUDServiceDAOImpl;
import ru.gradis.sovzond.model.dao.impl.ConfigDAOImpl;
import ru.gradis.sovzond.model.dao.impl.LoginDAOImpl;
import ru.gradis.sovzond.model.dao.impl.ReportDAOImpl;

import javax.sql.DataSource;

/**
 * Created by donchenko-y on 6/1/16.
 */

@Configuration
@ComponentScan(basePackages = "ru.gradis.sovzond")
@EnableWebMvc
public class RootContext extends WebMvcConfigurerAdapter {

	public DataSource dataSource;
//	BasicDataSource dbcp = new BasicDataSource();
//	DriverManagerDataSource dataSource = new DriverManagerDataSource();


	@Bean
	public DataSource getDataSource() {
		/**
		 * Lifray DataSource
		 */
		dataSource = InfrastructureUtil.getDataSource();
		/**
		 * Spring DataSource
		 * generate new connection
		 */
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:postgresql://192.168.42.21:5432/mo");
//		dataSource.setUsername("rc7postgres");
//		dataSource.setPassword("9PmAPWXHefUn");
		/**
		 * Apache pool DataSource
		 *
		 */
//		dbcp.setDriverClassName("org.postgresql.Driver");
//		dbcp.setUrl("jdbc:postgresql://192.168.42.21:5432/mo");
//		dbcp.setUsername("rc7postgres");
//		dbcp.setPassword("9PmAPWXHefUn");
		return dataSource;
	}


	@Bean
	public LoginDAO loginDAO() {
		return new LoginDAOImpl(getDataSource());
	}

	@Bean
	public ConfigDAO configDAO() {
		return new ConfigDAOImpl(getDataSource());
	}

	@Bean
	public CRUDServiceDAO crudServiceDAO() {
		return new CRUDServiceDAOImpl(getDataSource());
	}

	@Bean
	public ReportDAO reportDAO() {
		return new ReportDAOImpl(getDataSource());
	}

}
