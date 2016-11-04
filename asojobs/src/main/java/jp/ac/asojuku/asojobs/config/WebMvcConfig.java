package jp.ac.asojuku.asojobs.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import jp.ac.asojuku.asojobs.dao.BooksDAO;
import jp.ac.asojuku.asojobs.dao.impl.BooksDAOImpl;

@EnableWebMvc
@ComponentScan(basePackages="jp.ac.asojuku.asojobs")
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
		return new MappingJackson2HttpMessageConverter(
				Jackson2ObjectMapperBuilder.json().indentOutput(true).build());
	}
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
		converters.add(0,mappingJackson2HttpMessageConverter());
	}
	@Bean
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/contactdb");
		dataSource.setUsername("root");
		dataSource.setPassword("takatan10");
		
		return dataSource;
	}
	@Bean
	public BooksDAO getBooksDAO(){
		return new BooksDAOImpl(getDataSource());
	}
}
