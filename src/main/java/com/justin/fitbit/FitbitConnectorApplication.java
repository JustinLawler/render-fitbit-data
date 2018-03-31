package com.justin.fitbit;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import com.justin.fitbit.config.AppConfig;
import com.justin.fitbit.config.IPropsPersistance;
import com.justin.fitbit.scribe_oauth.CallFitbitAPI;


@SpringBootApplication
public class FitbitConnectorApplication implements CommandLineRunner
{
	private static final Logger logger = LogManager.getLogger(FitbitConnectorApplication.class);
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private IPropsPersistance propsPersistance;
	
	@Autowired
	private GenerateAccessToken generateAccessToken;
	
	@Autowired
	private CallFitbitAPI callFitbitApi;

	/**
	 */
	public static void main(String[] args)
	{
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");
		
		SpringApplication.run(FitbitConnectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		logger.debug("run()");
		
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		
		Arrays.stream(source.getPropertyNames()).forEach(s ->
        			System.out.printf("%s => %s%n", s, source.getProperty(s)));
		
		if (source.containsProperty("generateToken")) {
			generateAccessToken.generateToken();
		}
		else {
			String currentUserId = propsPersistance.loadCurrentUserId();
			String tokenStr = propsPersistance.loadAccessToken(currentUserId);
			//callFitbitApi.runRequest(callFitbitApi.buildService(), tokenStr, "activities/heart/date/2017-10-07/1d.json");
			callFitbitApi.runRequest(callFitbitApi.buildService(), tokenStr, "profile.json");
		}			
	}
}
