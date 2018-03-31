# Download health data - fitbit, emfit, etc


## Links
* oauth in java
	* https://github.com/scribejava/scribejava
	* https://dzone.com/articles/oauth-20-beginners-guide
* EmFit
	* http://blog.numbersinlife.com/2014/10/importing-data-from-emfit-sleep-monitor.html
	* https://forum.quantifiedself.com/t/omnier-com-new-api-aggregator/3969
* Scraping fitbit data from the website
	* https://dzone.com/articles/hey-fitbit-my-data-belong-to-me
* Fitbit & Oauth2
	* https://dev.fitbit.com/build/reference/web-api/oauth2/
	* https://github.com/alexthered/fitbitAPI20-scribe-java
	* https://medium.com/@5agado/a-quest-for-better-sleep-with-fitbit-data-analysis-5f10b3f548a
* Nokia/Withings Health
	* https://developer.health.nokia.com/api
* Diasend
	* https://www.glooko.com/resource/api-developers/
* Garmin
	* https://github.com/kjkjava/garmin-connect-export
* Foobot
	* 
* Trello
	* https://developers.trello.com/page/authorization
	* 



## TODO:
* Post Fitbit changes to GitHub
	* Scribe-java-fitbit
	* Justin's app
* Multiple tasks from the commandline
	* Date range or single date?
	* fitbit? Nikia? emfit?
	* What data? HR, Sleep, activity
	* output many different types of data
* Use groovy templates to render fitbit Data
	* https://stackoverflow.com/questions/14200041/access-json-nicely-from-groovy-templates
* Upgrade to get token with UI interface
	* https://github.com/Stasonis/fitbit-api-example-java
	* Some simple spring boot forms??
		* http://www.springboottutorial.com/creating-web-application-with-spring-boot
	* Extensible for mulitple results? 
* Build into own module on GitHub
* Migrate over to using spring oauth security
	+ https://github.com/Stasonis/fitbit-api-example-java
* Encrypt values
	* http://www.ru-rocker.com/2017/01/13/spring-boot-encrypting-sensitive-variable-properties-file/
	* https://security.stackexchange.com/questions/123393/google-oauth-2-0-and-java-is-it-safe-to-locally-store-access-tokens
* Make flexible for other apps
	* Moves
		* https://dev.moves-app.com/
	* Nokia health
		* https://developer.health.nokia.com/api


### Done
#### 2018-03-31
* Save tokens to local drive
	* ~/.oauth/.fitbit
* Update the 'get token' class to read from commandline
	* https://o7planning.org/en/10339/using-scribe-oauth-java-api-with-google-oauth-2-tutorial

## OAuth App
* On managing app - https://dev.fitbit.com/apps

### Details
* Name: 				Testing_OAuth_Acccess
* ClientID:				22CV6P
* Client Secret: 		04fba2a406cbd5bb4b5db0242782de08
* Auth URL: 			https://www.fitbit.com/oauth2/authorize
* Refresh Token URL:	https://api.fitbit.com/oauth2/token




