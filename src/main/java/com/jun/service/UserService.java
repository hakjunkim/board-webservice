package com.jun.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jun.repository.AdminRepositoryImpl;
import com.jun.repository.AuthoritiesRepositoryImpl;
import com.jun.repository.CustomerRepositoryImpl;
import com.jun.repository.UserRepositoryImpl;
import com.jun.schemas.types.Admin;
import com.jun.schemas.types.Customer;
import com.jun.schemas.types.UserType;
import com.jun.schemas.users.AddUserRequest;
import com.jun.schemas.users.AddUserResponse;
import com.jun.schemas.users.DeleteUserRequest;
import com.jun.schemas.users.DeleteUserResponse;
import com.jun.schemas.users.GetUserResponse;
import com.jun.schemas.users.UpdateUserRequest;
import com.jun.schemas.users.UpdateUserResponse;

@Service
public class UserService {
	
	@Autowired
	private AdminRepositoryImpl adminRepositoryImpl; 

	@Autowired
	private AuthoritiesRepositoryImpl authoritiesRepositoryImpl; 

	@Autowired
	private CustomerRepositoryImpl customerRepositoryImpl; 

	@Autowired
	private UserRepositoryImpl userRepositoryImpl; 

	public GetUserResponse getUser(String userName){
		GetUserResponse userResponse = new GetUserResponse();
		
		Customer customer = customerRepositoryImpl.getCustomer(userName);
		Admin admin = adminRepositoryImpl.getAdmin(userName);
		
		int status = 0;
		
		if(customer == null && admin == null) {
			status = 1; // No result with search with userName
		}else if(customer != null && admin == null){
			userResponse.setUserType(UserType.CUSTOMER);
			userResponse.setCustomer(customer);
		}else if(admin != null && customer == null){
			userResponse.setUserType(UserType.ADMIN);
			userResponse.setAdmin(admin);
		}else{
			status = 2; // The same userName in Customer and Admin
		}
		userResponse.setStatus(BigInteger.valueOf(status));
		
		return userResponse;
	}
	
	@Transactional
	public AddUserResponse addUser(AddUserRequest rq){
		
		AddUserResponse rs = new AddUserResponse();

		UserType rqUserType = rq.getUserType();
		
		rs.setStatus(BigInteger.valueOf(0));
		
		userRepositoryImpl.addUser(rq.getUser());
		
		if ( UserType.ADMIN == rqUserType ){
			adminRepositoryImpl.addAdmin(rq.getAdmin());
		}else if (UserType.CUSTOMER == rqUserType ){
			customerRepositoryImpl.addCustomer(rq.getCustomer());
		}else{
			rs.setStatus(BigInteger.valueOf(0));
		}
		
		return rs;
	}
	
	
	@Transactional
	public UpdateUserResponse updateUser(UpdateUserRequest rq){
		
		UpdateUserResponse rs = new UpdateUserResponse();
		
		UserType rqUserType = rq.getUserType();
		
		rs.setStatus(BigInteger.valueOf(0));
		
		if ( UserType.ADMIN == rqUserType ){
			adminRepositoryImpl.updateAdmin(rq.getAdmin());
		}else if (UserType.CUSTOMER == rqUserType ){
			customerRepositoryImpl.updateCustomer(rq.getCustomer());
		}else{
			rs.setStatus(BigInteger.valueOf(0));
		}
		
		return rs;
	}
	
	
	@Transactional
	public DeleteUserResponse deleteUser(DeleteUserRequest rq){
		
		DeleteUserResponse rs = new DeleteUserResponse();
		
		UserType rqUserType = rq.getUserType();
		
		rs.setStatus(BigInteger.valueOf(0));
		
		if ( UserType.ADMIN == rqUserType ){
			adminRepositoryImpl.deleteAdmin(rq.getUserName());
		}else if (UserType.CUSTOMER == rqUserType ){
			customerRepositoryImpl.deleteCustomer(rq.getUserName());
		}else{
			rs.setStatus(BigInteger.valueOf(0));
		}
		userRepositoryImpl.deleteUser(rq.getUserName());
		
		return rs;
	}
	
	
}
