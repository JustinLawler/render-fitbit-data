/**
 * 
 */
package com.justin.fitbit.scribe_oauth;

/**
 * 
 */
public enum ScopeEnum {
	ACTIVITY("activity"),
	HEART_RATE("heartrate"),
	LOCATION("location"),
	NUTRITION("nutrition"),
	PROFILE("profile"),
	SETTINGS("settings"),
	SLEEP("sleep"),
	SOCIAL("social"),
	WEIGHT("weight");
	
	private String scopeStr;

	/**
	 */
	private ScopeEnum(String scopeStr)
	{
		this.scopeStr = scopeStr;
	}
	
	/**
	 */
	public String getScopeStr()
	{
		return scopeStr;
	}
}
