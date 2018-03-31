/**
 * 
 */
package com.justin.fitbit.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author justinlawler
 */
@Component
public class JavaPropertiesPropsPersistance implements IPropsPersistance
{
	private static final String USER_ID_KEY = "user_id_key";
	
	@Autowired
	private AppConfig appConfig;
	
	
	@Override
	public String loadCurrentUserId()
	{
		File file = getUserDataFile();
		if (file == null || !file.exists()) {
			throw new IllegalStateException("No user file stored!");
		}
		
		Properties props = getProperties();
		String token = props.getProperty(USER_ID_KEY);
		if (token == null) {
			throw new IllegalStateException("no token found for " + USER_ID_KEY);
		}
		return token;
	}
	
	@Override
	public void saveCurrentUserId(String userId)
	{
		Properties props = getProperties();
		OutputStream os = null;
		try {
			File file = getUserDataFile();
			os = new FileOutputStream(file);
			props.setProperty(USER_ID_KEY, userId);
			props.store(os, "updating current user id to " + userId);
		}
		catch (IOException ioe) {
			throw new IllegalStateException(ioe);
		}
		finally {
			IOUtils.closeQuietly(os);
		}
	}
	

	/* (non-Javadoc)
	 * @see com.justin.fitbit.config.IPropsPersistance#loadAccessToken(java.lang.String)
	 */
	@Override
	public String loadAccessToken(String userId)
	{
		File file = getUserDataFile();
		if (file == null || !file.exists()) {
			throw new IllegalStateException("No user file stored!");
		}
		
		Properties props = getProperties();
		String token = props.getProperty(USER_ID_KEY + userId);
		if (token == null) {
			throw new IllegalStateException("no token found for user " + userId);
		}
		return token;
	}

	/* (non-Javadoc)
	 * @see com.justin.fitbit.config.IPropsPersistance#saveAccessToken(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveAccessToken(String userId, String accessToken) 
	{
		Properties props = getProperties();
		OutputStream os = null;
		try {
			File file = getUserDataFile();
			os = new FileOutputStream(file);
			String tokenKey = USER_ID_KEY + userId;
			props.setProperty(tokenKey, accessToken);
			props.store(os, "updating access token for user " + userId);
		}
		catch (IOException ioe) {
			throw new IllegalStateException(ioe);
		}
		finally {
			IOUtils.closeQuietly(os);
		}
	}
	
	/**
	 * @return
	 */
	private Properties getProperties()
	{
		Properties props = new Properties();
		File file = getUserDataFile();
		if (file != null && file.exists()) {
			InputStream is = null;			
			try {
				is = new FileInputStream(file);
				props.load(is);
			}
			catch (IOException ioe) {
				throw new IllegalStateException(ioe);
			}
			finally {
				IOUtils.closeQuietly(is);
			}
		}
		
		return props;
	}
	
	/**
	 */
	private File getUserDataFile()
	{
	    return new File(System.getProperty("user.home")
	    				+ File.separator
	    				+ ".oauth"
	    				+ File.separator
	    				+ "."
	    				+ appConfig.getAppName());
	}
}
