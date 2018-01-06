package com.studentapp.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

//Para usar lessThan
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;



public class ReusableSpecifications {

	public static RequestSpecBuilder reqSpec;
	public static RequestSpecification requestSpecification;
	
	public static ResponseSpecBuilder resSpec;
	public static ResponseSpecification responseSpecification;
	
	public static RequestSpecification getGenericRequestSpec() {
		reqSpec = new RequestSpecBuilder();
		reqSpec.setContentType(ContentType.JSON);
		requestSpecification = reqSpec.build();
		return requestSpecification;
	}
	
	public static ResponseSpecification getGenericResponseSpec() {
		resSpec = new ResponseSpecBuilder();
		resSpec.expectHeader("Content-Type", "application/json;charset=UTF-8");
		resSpec.expectHeader("Transfer-Encoding", "chunked");
		resSpec.expectResponseTime(lessThan(5L), TimeUnit.SECONDS);
		responseSpecification = resSpec.build();
		return responseSpecification;
		
	}
}
