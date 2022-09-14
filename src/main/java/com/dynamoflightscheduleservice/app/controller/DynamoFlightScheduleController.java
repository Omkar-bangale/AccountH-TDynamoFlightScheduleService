package com.dynamoflightscheduleservice.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dynamoflightscheduleservice.app.services.DynamoScheduleService;
import com.dynamoflightscheduleservice.app.model.SearchResult;
import com.dynamoflightscheduleservice.app.model.FlightSchedule;

@RestController
@RequestMapping("/flightschedule")
public class DynamoFlightScheduleController {
	
	@Autowired
	private DynamoScheduleService scheduleservice;
	
	@GetMapping("/getall")
	public List<FlightSchedule> gealltFlightsSchedule(@RequestHeader Map<String, String> headers){
DynamoFlightScheduleController.validateToken(headers);
if(validated)
		return scheduleservice.getallSchedules();
else 
	throw new RuntimeException("Token is not valid");
	}
	
	@PostMapping("/add")
	public String addSchedules(@RequestHeader Map<String, String> headers,@RequestBody FlightSchedule schedule) {
		DynamoFlightScheduleController.validateToken(headers);
		if(validated) {
		if(scheduleservice.addSchedule(schedule))
			return "Schedule Added successfully";
		else
			return "Error in adding a schedule";
		}
		else
			throw new RuntimeException("Token is not valid");
	}
	
	@PutMapping("/update")
	public String updateFlightSchedule(@RequestHeader Map<String, String> headers,@RequestBody FlightSchedule schedule, @RequestParam String flightName)
	{
		DynamoFlightScheduleController.validateToken(headers);
		if(validated) {
		if(scheduleservice.updateSchedule(schedule, flightName))
			return "Schedule updated successfully";
		else
			return "Error in updating a schedule";
		}
		else {
			throw new RuntimeException("Token is not valid");
		}
	}
	
	@DeleteMapping("/remove/{flightName}")
	public String removeFlightSchedule(@RequestHeader Map<String, String> headers,@PathVariable String flightName) {
		DynamoFlightScheduleController.validateToken(headers);
		if(validated) {
		if(scheduleservice.deleteSchedule(flightName))
			return "Schedule removed successfully";
		else
			return "Error in removing the schedule";
		}
		else
			throw new RuntimeException("Token is not valid");
	}
	
	@PostMapping("/getflights/{source}/{destination}")
	public List<SearchResult> searchBasedonSourceandDestination(@RequestHeader Map<String, String> headers,@PathVariable String source, @PathVariable String destination)
	{
		DynamoFlightScheduleController.validateToken(headers);
		if(validated)
		return  scheduleservice.searchforFlights(source, destination);
		else
			throw new RuntimeException("Token is not valid");
	}
	
private static boolean validated=false;
	
	
	public  static void validateToken (Map<String, String> header)
	{
	
		String token="";
		for(String key : header.keySet())
		{
			if(key.equals("authorization"))
				token=header.get(key);
		}
		HttpHeaders httpheader = new HttpHeaders();
		httpheader.set("Authorization", token);
		HttpEntity<Void> requestentity = new HttpEntity<>(httpheader);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> response = restTemplate.exchange("http://34.216.12.139:9004/validatejwt",HttpMethod.GET, requestentity,boolean.class);
		validated=response.getBody().booleanValue();
	}
}

