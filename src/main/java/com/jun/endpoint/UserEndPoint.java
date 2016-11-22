package com.jun.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.jun.repository.CustomerRepositoryImpl;
import com.jun.schemas.users.GetUserRq;
import com.jun.schemas.users.GetUserRs;

@Endpoint
public class UserEndPoint {

	private static final String NAMESPACE_URI = "http://jun.com/schemas/users";
	
	@Autowired
	CustomerRepositoryImpl customerRepositoryImpl;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserRq")
	@ResponsePayload
	public GetUserRs getUser(@RequestPayload GetUserRq rq){
		GetUserRs rs =  new GetUserRs();
		rs.setCustomer(customerRepositoryImpl.getCustomer(rq.getUserName()));
		return rs;
	}
	
}
