/**
 * 
 */
package com.justin.fitbit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 */
@Component
@ConfigurationProperties("config-properties")
public class AppConfig
{
	private String appName;
	private String clientId;
	private String clientSecret;
	private String callbackURL;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getCallbackURL() {
		return callbackURL;
	}
	
	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}
}
