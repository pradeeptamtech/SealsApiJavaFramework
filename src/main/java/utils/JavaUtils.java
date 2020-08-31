package utils;

import java.util.Base64;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import pojo.Credentials;

public class JavaUtils {
	
	// username:admin  password:admin123  admin:admin123
	public static Credentials  base64Encod(final String username,final String password) {
		String cred = username + ":" + password;
		
		try {
				cred = Base64.getEncoder().encodeToString(cred.getBytes());
				System.out.println(cred);
		}catch(Exception e) {
			
		}
		Credentials loginCred = new Credentials();
		loginCred.setCredentials(cred);
		
		return loginCred;
	}
	
	
	public static String randomNumber() {		
		Random random = new Random();
		int randNumber = random.nextInt(100);
		String id = Integer.toString(randNumber); 
		//System.out.println("Random Integer value: "+ randNumber);
		return id;
	
	}
	
	public static String randomString() {
		String randomString = RandomStringUtils.randomAlphabetic(5); 
		//System.out.println("Random String value: "+ randomString);
	
			return randomString;
	}

   
	/*public static void main(String[] args) {
    	JavaUtils util = new JavaUtils();
    	util.base64Encod("admin", "admin123");
    } */
	
}
