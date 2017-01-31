package com.jun.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.jun.repository.CustomerRepositoryImpl;
import com.jun.schemas.users.AddUserRequest;
import com.jun.schemas.users.AddUserResponse;
import com.jun.schemas.users.DeleteUserRequest;
import com.jun.schemas.users.DeleteUserResponse;
import com.jun.schemas.users.GetUserRequest;
import com.jun.schemas.users.GetUserResponse;
import com.jun.schemas.users.UpdateUserRequest;
import com.jun.schemas.users.UpdateUserResponse;
import com.jun.service.UserService;

@Endpoint
public class UserEndPoint {

	private static final String NAMESPACE_URI = "http://jun.com/schemas/users";
	
	@Autowired
	CustomerRepositoryImpl customerRepositoryImpl;
	
	@Autowired
	UserService userService;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRequest")
	@ResponsePayload
	public GetUserResponse getUser(@RequestPayload GetUserRequest rq){
		GetUserResponse rs =  userService.getUser(rq.getUserName());
		return rs;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddUserRequest")
	@ResponsePayload
	public AddUserResponse addUser(@RequestPayload AddUserRequest rq){
		AddUserResponse rs =  userService.addUser(rq);
		return rs;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
	@ResponsePayload
	public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest rq){
		UpdateUserResponse rs =  userService.updateUser(rq);
		return rs;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
	@ResponsePayload
	public DeleteUserResponse updateUser(@RequestPayload DeleteUserRequest rq){
		DeleteUserResponse rs =  userService.deleteUser(rq);
		return rs;
	}
	
}
