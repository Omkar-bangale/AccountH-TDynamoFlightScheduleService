package com.dynamoflightscheduleservice.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dynamoflightscheduleservice.app.model.FlightSchedule;
import com.dynamoflightscheduleservice.app.model.SearchResult;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class DynamoScheduleService {

	
	@Autowired
	private DynamoDbEnhancedClient enhancedClient;
	
	@Autowired
	 private DynamoDbTable<FlightSchedule> scheduleTable;
	
	@Autowired 
	private RestTemplate restTemplate;
	
	public boolean isAirlineBlocked(String airlineName)
	{
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://34.216.12.139:8081/ariline/status/"+airlineName, boolean.class);
		
	}
	
	public Integer areSeatsAvailable(String flightName)
	{
		RestTemplate template = new RestTemplate();
		return template.getForObject("http://34.216.12.139:8082/flight/seatsavailable/"+flightName, Integer.class);
	}
	
	public List<SearchResult> searchforFlights(String source, String destination)
	{
		List<FlightSchedule> flightschedules = scheduleTable.scan().items()
				.stream().filter(a-> a.getSource().equals(source) && a.getDestination().equals(destination))
				.collect(Collectors.toList());
		
		List<SearchResult> resultlist = new ArrayList<>();
		for(FlightSchedule schedule : flightschedules)
		{
			int temp=areSeatsAvailable(schedule.getFlightName());
			if(!isAirlineBlocked(schedule.getAirlineName()) && 
					temp>0) {
				SearchResult  result1= new SearchResult();
				
				result1.setAirlineName(schedule.getAirlineName());
				result1.setAvailableseats(temp);
				
				result1.setFlightName(schedule.getFlightName());
				
				result1.setSource(schedule.getSource());
				result1.setDestination(schedule.getDestination());
				result1.setFromdate(schedule.getFromdate());
				result1.setTodate(schedule.getTodate());
				resultlist.add(result1);
			}
			}
		return resultlist;
		}
	
	public List<FlightSchedule> getallSchedules()
	{
		return scheduleTable.scan().items().stream().collect(Collectors.toList());
	}
	
	
	
	public boolean addSchedule(FlightSchedule schedule)
	{
		scheduleTable.putItem(schedule);
		return true;
		
	}
	
	public boolean updateSchedule(FlightSchedule schedule, String flightName)
	{
		FlightSchedule schedule1 = scheduleTable.scan().items().stream()
				.filter(a -> a.getFlightName().equals(flightName)).findFirst().get();
		schedule1.setSource(schedule.getSource());
		schedule1.setDestination(schedule.getDestination());
		schedule1.setFromdate(schedule.getFromdate());
		schedule1.setTodate(schedule.getTodate());
		schedule1.setMealtype(schedule.getMealtype());
		scheduleTable.putItem(schedule1);
		return true;
	
	}
	
	public boolean deleteSchedule(String flightName)
	{
		FlightSchedule schedule = scheduleTable.scan().items().stream()
				.filter(a-> a.getFlightName().equals(flightName)).findFirst().get();
		scheduleTable.deleteItem(schedule);
		return true;
	}
	
	
}
