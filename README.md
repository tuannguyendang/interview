# README #
# weatherapp
Weather App Spring MVC + Hibernate (Maven)

Step to running app:
- Create database: https://github.com/tuannguyendang/weatherapp/blob/master/src/main/resources/weather.sql
- Run clean install for build project and run test case
- Run add tomcat
- come to link http://localhost:8080/weatherapp/search for testing assignment 1
	- search some city Thanh pho Ho Chi Minh or Ha Noi...
- come to link http://localhost:8080/weatherapp/shiftTime/re testing assignment 2

#Assignment

This documentation step running application.

### What is this repository for? ###

* Stock Price Service Tuan Nguyen Dang 
* Version 0.0.1

### Running application ###

* cd to folder stored project TuanNguyenDang
* Run maven command:
	- mvn clean install for dowload repository
	- mvn clean package for packaging and see test case run
* cd to target folder
* run command java -jar stockPriceService-0.0.1-SNAPSHOT.jar to running application
* press Ctrl + C to stop application on windows 
* API supported:
	* get close price http://localhost:8080//api/v2/GE/closePrice?startDate=2017-04-04&endDate=2017-04-04
	* get 200 day moving average http://localhost:8080/api/v2/GE/200dma?startDate=2016-10-14
	* get 200 day moving average of 1000 tickers http://localhost:8080/api/v2/200dma1000t?startDate=2017-04-04&tickers=FE,GE,null,DK,....	
	
### Contact ###

* tuan193@gmail.com for more information.
