package com.silverbullet.qualidnd.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Base class that all dao should inherit from
 * 
 * @author Batman
 *
 */
public class BaseDao {

	@Autowired
	JdbcTemplate template;
}
