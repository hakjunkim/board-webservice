package com.jun.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jun.schemas.types.Authority;
import com.jun.schemas.types.User;

@Repository
public class AuthoritiesRepositoryImpl {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Authority getAuthority(String userName){

		Authority authority = new Authority();
		
		List<Authority> authorities = jdbcTemplate.query(
					"SELECT * FROM authorities WHERE username = ?",
					new RowMapper<Authority>() {
						public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
							Authority a = new Authority(); 
							a.setUserName(rs.getString("username"));
							a.setAuthority(rs.getString("authority"));
							return a;
						}
					}
				);
		
		if (authorities.size() > 1){
			//FIXME Throw exception. userName should be unique. 
		}
		
		authority = authorities.get(0);
		
        return authority;
	}
	
	public int addAuthority(Authority authority) throws DataAccessException{
		
		String sql = "INSERT INTO authorities "+
					"(username, authority)" +
					"VALUES (?,?)";
		
		Object[] args = new Object[]{ authority.getUserName(),
									  authority.getAuthority() };
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
	
	
	public int updateAuthority(Authority authority) throws DataAccessException{
		
		String sql = "UPDATE authorities " +
					"SET username = ?, " + 
					"authority = ? + "+
					"WHERE username = ? ";
		
		Object[] args = new Object[]{ authority.getUserName(),
									  authority.getAuthority(),
									  authority.getUserName()};
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
	
	
	public int deleteAuthority(String userName) throws DataAccessException{
		
		String sql = "DELETE FROM authorities " +
					"WHERE username = ? ";
		
		Object[] args = new Object[]{ userName };
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
}
