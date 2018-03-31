/**
 * 
 */
package com.justin.fitbit;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.justin.fitbit.config.AppConfig;
import com.justin.fitbit.config.IPropsPersistance;
import com.justin.fitbit.scribe_oauth.CallFitbitAPI;

/**
 * More details on Evernote oAuth here:
 * 		- https://dev.evernote.com/doc/articles/authentication.php/
 * 
 * Web example code here:
 * 		- https://github.com/evernote/evernote-sdk-java/blob/master/sample/oauth/src/main/webapp/index.jsp
 *
 * Links
 *      - Fitbit oAuth library					- https://dev.fitbit.com/build/reference/web-api/oauth2/
 * 		- Uses Java oAuth library scribe 		- https://github.com/scribejava/scribejava
 * 		- Java scribe oAuth extended for Fitbit	- https://github.com/alexthered/fitbitAPI20-scribe-java
 *      - Edit Fitbit app 						- https://dev.fitbit.com/apps/details/22CV6P
 * 
 * TODO: update to use command-line input for the returned auth token in URL. See:
 *     - https://o7planning.org/en/10339/using-scribe-oauth-java-api-with-google-oauth-2-tutorial
 */
@Component
public class GenerateAccessToken
{
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private IPropsPersistance propsPersistance;
	
	@Autowired
	private CallFitbitAPI callFitbitAPI;
	
	/**
	 */
	public void generateToken() throws Exception
	{
		OAuth20Service service = callFitbitAPI.buildService();
		
		//Token scribeRequestToken = getRequestToken(service);
		System.out.println("Fetching the Authorization URL...");
		String authUrl = getAuthURL(service);
		System.out.println("Got the Authorization URL!");
	    System.out.println("Now go and authorize Scribe here:");
	    System.out.println();
	    
	    // Copy this URL and run in browser.
	    System.out.println(authUrl);
	    System.out.println();
	    
	    // Copy Authorization Code in browser URL and paste to Console
	    Scanner in = new Scanner(System.in);
	    System.out.println("And paste the full authorization redirect URL here");
	    System.out.print(">>");
	    String urlStr = in.nextLine();
	    System.out.println();
		
		//String urlStr = "http://justinlawler.net/?code=c9618d18d9e566abe46db14b62aebe08e83e7f45#_=_";
	    System.out.println("Trading the Request Token for an Access Token...");
		URI uri = new URI(urlStr);
		List<NameValuePair> params = URLEncodedUtils.parse(uri, Charset.forName("UTF-8"));
		String oauth_verifier = params.get(0).getValue();
		OAuth2AccessToken token = getAccessToken(service, oauth_verifier); 
		String tokenStr = token.getAccessToken();
		String userId = getUserId(token);
		propsPersistance.saveCurrentUserId(userId);
		propsPersistance.saveAccessToken(userId, tokenStr);
		
		// test with latest token
		System.out.println("Now we're going to access a protected resource...");
		callFitbitAPI.runRequest(service, tokenStr, "profile.json");
		
		in.close();
	}
	
	/**
	 * Returns something like:
	 * 		https://www.fitbit.com/oauth2/authorize?
	 * 				response_type=code
	 * 				&client_id=22CV6P
	 * 				&redirect_uri=http%3A%2F%2Fjustinlawler.net
	 * 				&scope=activity%2520profile
	 */
	public String getAuthURL(OAuth20Service service)
	{
		
		String authUrl = service.getAuthorizationUrl();
		
//		System.out.println("++++++ Auth URL +++++++++");
//		System.out.println("Auth URL:            " + authUrl);
//        System.out.println("++++++++++++++++++++++++++++++");
        
        return authUrl;
	}
	
	/**
	 * Once user navigates & validates on Fitbit, they will be redirected to something like:
	 *		http://justinlawler.net/?code=6af2763fb5d98f29bd3a022d6b6c084c96af969f#_=_
	 */
	private OAuth2AccessToken getAccessToken(
				OAuth20Service service,
				String oauth_verifier) throws Exception
	{
		OAuth2AccessToken accessToken = service.getAccessToken(oauth_verifier);
		
		String userId = getUserId(accessToken);
                
        System.out.println("++++++ Access Token +++++++++");
        System.out.println("User Id:		" + userId);
        System.out.println("Token:		" + accessToken.getAccessToken());
        System.out.println("Type:		" + accessToken.getTokenType());
        System.out.println("Scope:		" + accessToken.getScope());
        System.out.println("+++++++++++++++++++++++++++++");
        
        return accessToken;
	}
	
	/**
	 * scribe doesn't parse out the Fitbit userId. Do it manally
	 */
	private String getUserId(OAuth2AccessToken accessToken) throws Exception
	{
		JSONObject obj = new JSONObject(accessToken.getRawResponse());
		String userId = obj.getString("user_id");
		
		return userId;
	}
}
