package com.jun.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jun.schemas.types.Customer;
import com.jun.schemas.types.User;

@Repository
public class UserRepositoryImpl {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public User getUser(String userName){

		User user = new User();
		
		List<User> users = jdbcTemplate.query(
				"SELECT * FROM users WHERE username = ?",
					new RowMapper<User>() {
						public User mapRow(ResultSet rs, int rowNum) throws SQLException {
							User u = new User(); 
							u.setUserName(rs.getString("username"));
							u.setPassword(rs.getString("password"));
							u.setEnabled(rs.getInt("enabled"));
							return u;
						}
					}
				);
		
		if (users.size() > 1){
			//FIXME Throw exception. userName should be unique. 
		}
		
		user = users.get(0);
			
		
        return user;
	}
	
	public int addUser(User user) throws DataAccessException{
		
		String sql = "INSERT INTO users "+
					"(username, password, enabled)" +
					"VALUES (?,?,?)";
		
		Object[] args = new Object[]{ user.getUserName(),
									  user.getPassword(),
									  user.getEnabled() };
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
	
	
	public int updateUser(User user) throws DataAccessException{
		
		String sql = "UPDATE users " +
					"SET username = ?, " + 
					"password = ?, enabled = ?" + 
					"WHERE username = ? ";
		
		Object[] args = new Object[]{ user.getUserName(),
									  user.getPassword(),
									  user.getEnabled(),
									  user.getUserName()};
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
	
	
	public int deleteCustomer(String userName) throws DataAccessException{
		
		String sql = "DELETE FROM users " +
					"WHERE username = ? ";
		
		Object[] args = new Object[]{ userName };
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
}
