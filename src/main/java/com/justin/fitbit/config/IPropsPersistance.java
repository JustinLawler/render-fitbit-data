/**
 * 
 */
package com.justin.fitbit.config;

/**
 */
public interface IPropsPersistance
{
	
	/**
	 */
	public String loadCurrentUserId();
	
	/**
	 */
	public void saveCurrentUserId(String userId);
	
	/**
	 */
	public String loadAccessToken(String userId);
	
	/**
	 */
	public void saveAccessToken(String userId, String accessToken);
}
