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

import com.jun.schemas.types.Authority;
import com.jun.schemas.types.Customer;

@Repository
public class CustomerRepositoryImpl {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Customer getCustomer(String userName){

		Customer customer = new Customer();
		
		List<Customer> customers = jdbcTemplate.query(
				"SELECT * FROM customers WHERE username = ?",
				new Object[]{userName},
					new RowMapper<Customer>() {
						public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
							Customer c = new Customer(); 
							c.setUserName(rs.getString("username"));
							c.setFirstName(rs.getString("first_name"));
							c.setMiddleName(rs.getString("middle_name"));
							c.setLastName(rs.getString("last_name"));
							c.setHomePhone(rs.getString("homep_hone"));
							c.setMobilePhone(rs.getString("mobile_phone"));
							try {
								c.setCDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(rs.getString("c_datetime")));
								c.setUDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(rs.getString("u_datetime")));
							} catch (DatatypeConfigurationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return c;
						}
					}
				);
		
		if (customers.size() > 1 || customers.size() == 0){
			//FIXME Throw exception. userName should be unique.
			customer = null;
		}
		else{
			customer = customers.get(0);
		}
		
        return customer;
	}
	
	public int addCustomer(Customer customer) throws DataAccessException{
		
		String sql = "INSERT INTO customers "+
				"(username, first_name, middle_name, last_name, home_phone, mobile_phone, c_datetime, u_datetime)" +
				"VALUES (?,?,?,?,?,?,NOW(), NOW())";
		
		Object[] args = new Object[]{ customer.getUserName(),
									  customer.getFirstName(),
									  customer.getMiddleName(),
									  customer.getLastName(),
									  customer.getHomePhone(),
									  customer.getMobilePhone() };
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
	
	
	public int updateCustomer(Customer customer) throws DataAccessException{
		
		String sql = "UPDATE customers " +
					"SET username = ?, " + 
					"first_name = ?, middle_name = ?, last_name = ?, " + 
					"home_phone = ?, mobile_phone = ?, u_datetime = NOW()" + 
					"WHERE username = ? ";
		
		Object[] args = new Object[]{ customer.getUserName(),
									  customer.getFirstName(),
									  customer.getMiddleName(),
									  customer.getLastName(),
									  customer.getHomePhone(),
									  customer.getMobilePhone(),
									  customer.getUserName()};
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}
	
	
	public int deleteCustomer(String userName) throws DataAccessException{
		
		String sql = "DELETE FROM customers " +
					"WHERE username = ? ";
		
		Object[] args = new Object[]{ userName };
		
		int rows = jdbcTemplate.update(sql,args);
		
		return rows;
	}	
}
