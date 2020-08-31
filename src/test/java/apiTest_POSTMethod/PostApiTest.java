package apiTest_POSTMethod;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import apiBuilder.PostAPIBuilder;
import apiConfig.APIPath;
import apiConfig.HeaderConfigs;
import apiVerification.APIVerification;
import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.JavaUtils;
import utils.UtilityApiMethods;

public class PostApiTest extends BaseTest {
	
	//@Test
	public void validPostAPITest()
	{
		test.log(LogStatus.INFO,"My Post request started......");
		
		HeaderConfigs header = new HeaderConfigs();
		PostAPIBuilder postBuilder = new PostAPIBuilder();
		//JavaUtils  utils = new JavaUtils();
				
		Response response= RestAssured.given().when().headers(header.DefaultHeaders()).
				body(postBuilder.postRequestBody(JavaUtils.randomNumber(),JavaUtils.randomString(),JavaUtils.randomString())).
				when().post(APIPath.apiPath.CREATE_POST);
		
		System.out.println("Body---> " + response.getBody().asString());
		System.out.println("Status Code---> "+ response.getStatusCode());
		//System.out.println("title---> "+ response);
		
		
		UtilityApiMethods.ResponseKeyValidationFromJsonObject(response, "author");
		UtilityApiMethods.responseCodeValidation(response, 200);
		UtilityApiMethods.ResponseTimeValidation(response);
		
		test.log(LogStatus.INFO,"My Post request Ended......");
	}

	
	//@Test
	public void reqBin_PostAPITest()
	{
		String Id= Integer.toString(12345);
		String customer ="Janu";
		String quantity = Integer.toString(1);
		String price= Double.toString(10.00);
		
		HeaderConfigs header = new HeaderConfigs();
		PostAPIBuilder postBuilder = new PostAPIBuilder();
		//JavaUtils  utils = new JavaUtils();
				
		Response response= RestAssured.given().when().headers(header.DefaultHeaders()).
				body(postBuilder.reqbin_PostApieReuestBody(Id,customer,quantity,price)).
				when().post(APIPath.apiPath.reqbin_postApi);
		
		System.out.println("Body---> " + response.getBody().asString());
		System.out.println("Status Code---> "+ response.getStatusCode());
		System.out.println("Response Time---> "+ response.time());
		System.out.println("Response Size---> "+ response.asByteArray());
		System.out.println("json format---> "+ response.prettyPrint());
		
		
		UtilityApiMethods.responseCodeValidation(response, 200);
		UtilityApiMethods.ResponseTimeValidation(response);
	}
	

}
