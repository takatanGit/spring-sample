package jp.ac.asojuku.asojobs.dao.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import jp.ac.asojuku.asojobs.dao.BooksDAO;

public class BooksDAOImpl implements BooksDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public BooksDAOImpl(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
