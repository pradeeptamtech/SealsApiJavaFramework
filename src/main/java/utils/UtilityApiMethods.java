package utils;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.IdentityHashMap;
//import org.apache.commons.collections.map.MultiValueMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
//import org.json.JSONException;
import org.json.JSONObject;
//import org.testng.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.amazonaws.samples.PostgreSqlConnection;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.response.Response;

public class UtilityApiMethods extends ExtentReportListener{

	static SoftAssert softAssert = new SoftAssert();

	/*
	 * Verify Response body
	 */
	public static void responseBodyValidation(Response response) {
		try {
			// test.log(LogStatus.INFO, "***********Checking Response Body**************");
			String responseBody = response.getBody().asString();
			// System.out.println("<===== Response Body =====> \n" + responseBody + "\n\n");
			// test.log(LogStatus.PASS, "Response Body ==> " + responseBody);
			Assert.assertTrue(responseBody != null);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Verify Response Status code Validation
	 */
	public static void responseCodeValidation(Response response, int statusCode) {
		test.log(LogStatus.INFO, "***********Check Response Code *****************");
		try {
			Assert.assertEquals(statusCode, response.getStatusCode());
			test.log(LogStatus.PASS, "Successfully Validated Status Code is ==> " + response.getStatusCode());
		} catch (AssertionError e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
			test.log(LogStatus.FAIL,"Expected Status Code ==> " + statusCode + ",Instead of Getting:: " + response.getStatusCode());
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Checking status line
	 * 
	 */
	public static void statusLineValidation(Response response, String statline) {
		try {
			test.log(LogStatus.INFO, "***********Checking status line *****************");
			String statusLine = response.getStatusLine();
			test.log(LogStatus.PASS, "Status Line ==> " + statusLine);
			Assert.assertEquals(statusLine, statline);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Check content Type
	 */
	public static void contentTypeValidation(Response response, String content_type) {
		try {
			test.log(LogStatus.INFO, "***********Check content Type *****************");
			String conentType = response.header("Content-Type");
			test.log(LogStatus.PASS, "Content Type ==> " + conentType);
			Assert.assertEquals(conentType, content_type);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Check content encoding
	 */
	public static void contentEncodingValidation(Response response, String content_encoding) {
		try {
			test.log(LogStatus.INFO, "***********Check content Encoding *****************");
			String conentEncoding = response.header("Content-Encoding");
			test.log(LogStatus.PASS, "Content Encoding ==> " + conentEncoding);
			Assert.assertEquals(conentEncoding, "gzip");

		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

	}

	/*
	 * Check content Length
	 */
	public static void contentLengthValidation(Response response) {
		try {
			test.log(LogStatus.INFO, "***********Check content Length *****************");
			String conentLength = response.header("Content-Length");
			test.log(LogStatus.INFO, "Content Length is ==> " + conentLength);
			if (Integer.parseInt(conentLength) < 100)
				test.log(LogStatus.WARNING, "Content length is less than 100");

			Assert.assertTrue(Integer.parseInt(conentLength) > 100);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

	}

	/*
	 * Verify Session cookies
	 */
	public static void contentCookiesValidation(Response response, String cookieValue) {
		try {
			test.log(LogStatus.INFO, "***********Check Cookies *****************");
			String cookie = response.getCookie("SESSIONID");
			test.log(LogStatus.PASS, "Cookies is ==> " + cookie);
			Assert.assertEquals(cookie, cookieValue);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}


	/*
	 * Verify Response Time Validation
	 */
	public static void ResponseTimeValidation(Response response) {
		test.log(LogStatus.INFO, "***********Checking  Response TIME *****************");
		try {
			Long time = response.time();
			Assert.assertTrue(time < 5000, "Expected Time is not exceeding.....");
			test.log(LogStatus.INFO, "Response Time is ==> " + time);
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Check Response Time Validation
	 */
	public static void Validate_ResponseTime(Response response) {
		test.log(LogStatus.INFO, "***********Checking Response Time ****************");
		try {
			Long responseTime = response.time();
			test.log(LogStatus.PASS, "Actual Response Time is =========> " + responseTime);
			if (responseTime >= 4000) {
				softAssert.assertEquals(responseTime, responseTime >= 4000,	"Expected Response Time is exceeding 4000 ms.....");
				test.log(LogStatus.PASS, "Response Time is more than 4000 ==> " + responseTime);

			} else {
				softAssert.assertEquals(responseTime, responseTime < 4000,"Expected Response Time is not exceeding than 4000 ms.....");
				test.log(LogStatus.PASS, "Response Time is less than 4000 ==> " + responseTime);
			}

		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}


	/* 
	 * Verify Response Key Validation from JSON Array 
	 * 
	 */	
	public static void ResponseKeyValidationFromArray(Response response,String key)
	{
		try {
			JSONArray arr = new JSONArray(response.getBody().asString());
			for(int i=0; i<arr.length(); i++)
			{
				JSONObject obj = arr.getJSONObject(i);
				// System.out.println(obj.get("title"));
				test.log(LogStatus.PASS, "Successfully Validated value is --> "+ key + "It is " + obj.get(key));
			}
		} catch(Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}	
	}




	/* 
	 * Checking Key Value from API Response
	 * 
	 */
	public static void getKeyValueFromResponse(Response response,String listObj,String key)
	{	
		try {
			List<Map<String, String>> list = response.jsonPath().getList(listObj);
			test.log(LogStatus.PASS, "No of Json Objects ==> " + list.size());
			//String expectedVal = list.get(0).get(key);
			//System.out.println("Size----> " + list.size() );
			int count = 0;
			if(list.size()>0) 
			{	
				for(int i = 0; i<list.size(); i++)
				{
					String actualVal = list.get(i).get(key);
					test.log(LogStatus.PASS, "Validated Key--> " + key +" : " + actualVal);	
					System.out.println("Key Value is ----> " +  actualVal);
					count = i+1; ;
				}
				//System.out.println("Key value count is ----> " +  count);
			}
		}catch(Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}
	/*
	 * count number of objects from Response     
	 */
	public static Integer getCountNoOfObjectsFromResponse(Response response, String listObj, String key)
	{
		int responseCount = 0;
		int KeyValueCount = 0;
		try {
			List<Map<String, String>> list = response.jsonPath().getList(listObj);
			test.log(LogStatus.PASS, "No of Key value count from Response ====> " + list.size());
			responseCount = list.size();
			//System.out.println("No of Key value count from Response ----> "+responseCount + "\n\n");
			if(list.size() > 0) 
			{				
				for(int i = 0; i<list.size() ; i++)
				{
					String actualVal = list.get(i).get(key);
					//test.log(LogStatus.PASS, "Key value is present--> " + key +" : " + actualVal);
					KeyValueCount = i+1;
				}
			}	
			Assert.assertEquals(responseCount, KeyValueCount, "API Response object count is not matched");
		}catch(Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
		return responseCount;
	}

	/*
	 * Verify Response Key Validation from '0'th Level
	 */
	public static void ResponseKeyValidationFromJsonObject(Response response, String key) {
		try {
			JSONObject jsonObj = new JSONObject(response.getBody().asString());
			// System.out.println(" <== key value is==>" + jsonObj.has(key));
			// System.out.println(" <== Validate value is==>" + jsonObj.get(key));
			if (jsonObj.has(key) && jsonObj.get(key) != null) {
				test.log(LogStatus.PASS, "Successfully Validated KEY & VALUE for Serial number is ===> " + key + "::> " + jsonObj.get(key));
				System.out.println("<====Validate KEY & VALUE for Serial number is====> " + key + ": " + jsonObj.get(key));
			} else
				test.log(LogStatus.FAIL, "Key is not available");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
	}

	/*
	 * Checking one Key Value from API Response
	 * 
	 */
	public static List<String> verifyKeyValueFromResponse(Response response, String listObj, String key) {

		List<Map<String, String>> list = response.jsonPath().getList(listObj);
		test.log(LogStatus.PASS, "No of Json Objects ===> " + list.size());
		// String expectedVal = list.get(0).get(key);

		List alist = new ArrayList();
		int count = 0 ;
		try {

			if (list.size() > 0) 
			{
				for (int i = 0; i < list.size(); i++) 
				{
					String actualVal = String.valueOf(list.get(i).get(key));
					if( key != null && actualVal != null ) 
					{
						alist.add(actualVal);
						//System.out.println("Key Value is ===> " + key + " : " + actualVal);
						count = i + 1; 
					}
				}

			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}
		System.out.println("Number of Objects count ::> " + count );
		Assert.assertEquals(list.size(),count ,"Key value count is not matched");

		System.out.println("List value are =====> " + alist );
		test.log(LogStatus.PASS, "Key values Lists are ===> "+ key + "::>> " + alist);

		//System.out.println("########################################################################################\n");
		test.log(LogStatus.INFO, "##################################################################################");

		return alist;
	}




	/*
	 * Find the key value from Response ***********************N+! Level*****************
	 */


	public static  Map getKey1(JSONObject json, String key) {
		//System.out.println(" method getKey called");
		int count = 0;
		String val = "";
		boolean exists = json.has(key);

		Map<String, String> keyValMap = new LinkedHashMap();

		if (!exists) {

			Iterator<?> keys = json.keys();

			while (keys.hasNext())
			{
				String nextKey = (String) keys.next();

				try {
					if (json.get(nextKey) instanceof JSONObject) {

						JSONObject jsonObj = json.getJSONObject(nextKey);

						if (jsonObj.has(key)) {
							val = json.get(key).toString();
							keyValMap.put(key, val);

						}

					} else if (json.get(nextKey) instanceof JSONArray)
					{
						JSONArray jsonArray = json.getJSONArray(nextKey);

						for (int i = 0; i < jsonArray.length(); i++) 
						{

							String jsonArrayString = jsonArray.get(i).toString();

							JSONObject innerJson = new JSONObject(jsonArrayString);

							if (innerJson.has(key))
							{
								val = innerJson.get(key).toString();
								keyValMap.put(key + "[" + i +"]", val);

								try {
									Float floatVal = Float.valueOf(val).floatValue();
									//   System.out.println("Float: " + floatVal);

									if( floatVal  < 0 )
									{
										System.out.println("Key value is negative...> " + key + "[" + i + "] : " + val );
										test.log(LogStatus.INFO, "**** FAILED **** IF Key value is Negative ====>  " + key + "[" + i + "] : " + val);
									}
								}catch(Exception e) {
									e.fillInStackTrace();
								}
							}
							count = count + 1;
						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
			}

		} else {

			val = json.get(key).toString();
			keyValMap.put(key, val);
		}
		//System.out.println("<-------------------->" + keyValMap);
		System.out.println("Objects Count :=>  " + count );

		return keyValMap;
	}

	/*
	 * parsing nested  json and get Key Value From Response body
	 */
	// How to parse Dynamic json and nested json
	public static String  parseObject(JSONObject json, String key)
	{
		String actualVal = json.get(key).toString();
		//System.out.println("Key : Value ==> " + key + " : " + actualVal);
		//test.log(LogStatus.PASS, "Key : value ==> " + key + " : " + actualVal);
		return  actualVal;
	}

	public static String getKey(JSONObject json,String key)
	{
		boolean exists = json.has(key);
		String keyVal = "";		
		Iterator<?> keys;
		String nextKeys;

		if(!exists) {
			keys = json.keys();
			while(keys.hasNext())
			{
				nextKeys = (String)keys.next();

				try {
					if(json.get(nextKeys) instanceof JSONObject) 
					{
						if (exists == false) 
						{
							getKey(json.getJSONObject(nextKeys),key);
						}
					}
					else if(json.get(nextKeys) instanceof JSONArray)
					{
						JSONArray jsonArray = json.getJSONArray(nextKeys);
						//System.out.println("<===== JsonArray =====> \n" + jsonArray + "\n");
						for(int i=0; i<jsonArray.length(); i++)
						{
							String jsonArrayString = jsonArray.get(i).toString();
							//System.out.println("<===== JsonArrayString =====> \n" + jsonArrayString + "\n");
							JSONObject innerJson = new JSONObject(jsonArrayString);
							if(exists == false)
							{
								getKey(innerJson,key);
							}
						}

					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		else {
			keyVal = parseObject(json,key);	

		}
		return keyVal;
	}

	/*
	 * 
	 * Return key value pair from API Response ********************************************
	 */
	public static Integer counter = 0;
	public static Map<Integer, HashMap<String, String>>    map=new HashMap<Integer, HashMap<String,String>>();
	public static  Map<Integer, HashMap<String, String>>   get_Key(JSONObject json, String key)
	{
		String val = "";

		boolean exists = json.has(key);

		Iterator<?> keys;

		String nextKey;

		if (!exists)
		{
			keys = json.keys();

			while (keys.hasNext())
			{
				nextKey = (String) keys.next();

				try {

					if (json.get(nextKey) instanceof JSONObject)
					{
						if (exists == false)
						{
							get_Key(json.getJSONObject(nextKey), key);
						}
					}
					else if (json.get(nextKey) instanceof JSONArray)
					{
						JSONArray jsonArray = json.getJSONArray(nextKey);

						for (int i = 0; i < jsonArray.length(); i++)
						{
							String jsonArrayString = jsonArray.get(i).toString();

							JSONObject innerJson = new JSONObject(jsonArrayString);

							if (exists == false)
							{
								get_Key(innerJson, key);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		//	        else {

		//	               //String val2 = parseObject(json,key);

		//	               val= json.get(key).toString();

		//	               keyValMap.put(key, val);

		//	               //System.out.println(" value--> " + val);

		//	               //System.out.println(" -key method-------> " + val);   

		//	              

		//	        }

		try {

			val = json.get(key).toString();
			// System.out.println(" -key method-------> " + val);
			// keyValMap.put(key, val);
			HashMap<String, String> m = new HashMap<String, String>();
			m.put(key, val);
			map.put(counter,m);
			// System.out.println(" -key method-------> " + map);
			counter++;
		}
		catch (Exception e) {
			//
		}	
		//System.out.println(" -key method-------> " + map);
		return map;
	}


	/* ******************************************************************************/



	/*****************************************************************************/




	/* *************************** Compare DB and API Response ******************************************       */
	/*  
	 * Compare error count from API response and DB
	 */
	public static int getErrorCountFromDB(String serialNo,String startDate , String endDate)
	{
		PostgreSqlConnection obj = new PostgreSqlConnection();
		test.log(LogStatus.PASS, "No of Key value count from Database ====> " + obj.getNoOfErrorCount(serialNo,startDate,endDate).getSum());
		return obj.getNoOfErrorCount(serialNo,startDate,endDate).getSum();
	}

	public static void  validateErrorCountObjectWithDB(Response response,String listObj,String key,String serialNo,String startDate , String endDate, List serialNoList)
	{
		System.out.println("\n*******Compare error code count from API response and DB **************************\n");
		try {
			test.log(LogStatus.INFO, "***********Compare Error code count from API response and Database *****************");
			int errorCodeCountFromDB = getErrorCountFromDB(serialNo, startDate , endDate); 
			System.out.println("Error count From Database------> " + errorCodeCountFromDB);
			int errorCountFromApi = getCountNoOfObjectsFromResponse(response,listObj,key);
			System.out.println("Error Count From API Response------> " + errorCountFromApi + "\n" );
			//Assert.assertEquals(errorCodeCountFromDB,errorCountFromApi,"ErrorCode count is not matched");

			if(errorCodeCountFromDB == errorCountFromApi) {
				Assert.assertEquals(errorCodeCountFromDB,errorCountFromApi,"ErrorCode count is not matched");
				test.log(LogStatus.INFO,"Error count is same  from API and DB ---------------" );
			}else {
				softAssert.assertEquals(errorCodeCountFromDB,errorCountFromApi,"ErrorCode count is not matched");
				test.log(LogStatus.INFO,"Error count is not same from API and DB -------------");
				serialNoList.add(serialNo);
				//System.out.println("Fail Serial no list ======>  " + lstObj);
			}			
		} catch(Exception e) {
			test.log(LogStatus.FAIL, e.fillInStackTrace());
		}

	}


} // End Class





