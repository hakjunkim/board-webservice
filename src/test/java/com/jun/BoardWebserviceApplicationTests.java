package com.jun;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jun.repository.CustomerRepositoryImpl;
import com.jun.schemas.types.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardWebserviceApplicationTests {

	@Autowired
	CustomerRepositoryImpl customerRepositoryImpl;
	
	@Test
	public void contextLoads() {
		
		String userName = "jun";
		Customer c = customerRepositoryImpl.getCustomer(userName);
		if ( c == null ){
			
		}else{
			assertTrue(c.getUserName().equals(userName));
		}
		
	}
	
}
