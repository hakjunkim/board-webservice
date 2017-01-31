package com.jun.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jun.schemas.types.Admin;

@Repository
public class AdminRepositoryImpl {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Admin getAdmin(String userName){

		Admin admin = new Admin();
		
		List<Admin> admins = jdbcTemplate.query(
				"SELECT * FROM admins WHERE username = ?",
				new Object[]{userName},
					new RowMapper<Admin>() {
						public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
							Admin a = new Admin(); 
							a.setUserName(rs.getString("username"));
							a.setFirstName(rs.getString("first_name"));
							a.setMiddleName(rs.getString("middle_name"));
							a.setLastName(rs.getString("last_name"));
							try {
								a.setCDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(rs.getString("c_datetime")));
								a.setUDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(rs.getString("u_datetime")));
							} catch (DatatypeConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return a;
						}
					}
				);
		
		if (admins.size() > 1 || admins.size() == 0){
			//FIXME Throw exception. userName should be unique.
			admin = null;
		}
		else{
			admin = admins.get(0);
		}
		
        return admin;
	}
	
	public int addAdmin(Admin admin) throws DataAccessException{
		
		String sql = "INSERT INTO admins "+
				"(username, first_name, middle_name, last_name, c_datetime, u_datetime)" +
				"VALUES (?,?,?,?, NOW(), NOW())";
		
		Object[] args = new Object[]{ admin.getUserName(),
										admin.getFirstName(),
										admin.getMiddleName(),
										admin.getLastName()};
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
	
	
	public int updateAdmin(Admin admin) throws DataAccessException{
		
		String sql = "UPDATE admins " +
					"SET username = ?, " + 
					"first_name = ?, middle_name = ?, last_name = ?, " + 
					"home_phone = ?, mobile_phone = ?, u_datetime = NOW()" + 
					"WHERE username = ? ";
		
		Object[] args = new Object[]{ admin.getUserName(),
									admin.getFirstName(),
									admin.getMiddleName(),
									admin.getLastName(),
									admin.getUserName()};
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
	
	
	public int deleteAdmin(String userName) throws DataAccessException{
		
		String sql = "DELETE FROM admins " +
					"WHERE username = ? ";
		
		Object[] args = new Object[]{ userName };
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}	
}
