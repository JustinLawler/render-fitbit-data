/**
 * 
 */
package com.justin.fitbit.scribe_oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.justin.fitbit.config.AppConfig;
import com.justin.fitbit.config.IPropsPersistance;

/**
 */
@Component
@ConfigurationProperties("call-fitbit-api")
public class CallFitbitAPI
{
	static final FitbitApi20 FITBIT_SERVICE = FitbitApi20.instance();
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private IPropsPersistance propsPersistance;
	
	private String fitbitAuthUrl;
	
	// Full list here: https://dev.fitbit.com/build/reference/web-api/oauth2/#scope
	private String fitbitScope;
	
	
	public String getFitbitAuthUrl() {
		return fitbitAuthUrl;
	}

	public void setFitbitAuthUrl(String fitbitAuthUrl) {
		this.fitbitAuthUrl = fitbitAuthUrl;
	}
	
	public String getFitbitScope() {
		return fitbitScope;
	}

	public void setFitbitScope(String fitbitScope) {
		this.fitbitScope = fitbitScope;
	}
	
	/**
	 */
	public OAuth20Service buildService() throws Exception
	{
		OAuth20Service service = new ServiceBuilder(appConfig.getClientId())
                .apiSecret(appConfig.getClientSecret())
                .scope(fitbitScope)
                .callback(appConfig.getCallbackURL())
                .build(FITBIT_SERVICE);
		
		return service;
	}

	/**
	 * Example CURL
	 * 
	 * curl -X GET \
	 *	https://api.fitbit.com/1/user/3BZ2KZ/profile.json \
  	 *		-H 'authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzQloyS1oiLCJhdWQiOiIyMkNWNlAiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJzY29wZXMiOiJ3aHIgd3BybyB3YWN0IiwiZXhwIjoxNTIyMjYxMjAxLCJpYXQiOjE1MjIyMzI0MDF9.SAXMRF-5hrSR8TLExmWx06qYa-UGFPVpEr4_fLsQxuw'
  	 *
  	 * Other examples:
  	 *		- https://api.fitbit.com/1/user/3BZ2KZ/activities/date/2018-03-26.json
	 */
	public void runRequest(
				OAuth20Service service,
				String accessToken,
				String requestUrl) throws Exception
	{
		String fullURL = fitbitAuthUrl + propsPersistance.loadCurrentUserId() + "/" + requestUrl;
		final OAuthRequest request = new OAuthRequest(Verb.GET, fullURL);
		service.signRequest(accessToken, request); // the access token from step 4
		final Response response = service.execute(request);
		prettyPrintJson(response.getBody());
	}
	
	/**
	 */	
	private static final void prettyPrintJson(String jsonStr) throws Exception
	{
		org.json.JSONObject jsonObject = new org.json.JSONObject(jsonStr);
		System.out.println(jsonObject.toString(4));
	}
}
