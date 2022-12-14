package com.dynamoflightscheduleservice.app.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class FlightSchedule {

	
	private String flightName;
	private String airlineName;
	private String scheduleId;
	private String source;
	private String destination;
	private String fromdate;
	private String todate;
	private String mealtype;
	
	@DynamoDbAttribute(value = "flightName")
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	@DynamoDbAttribute(value = "airlineName")
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	
	@DynamoDBAutoGeneratedKey
	@DynamoDbPartitionKey
	@DynamoDBHashKey(attributeName="scheduleId")
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	@DynamoDbAttribute(value = "source")
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@DynamoDbAttribute(value = "destination")
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	@DynamoDbAttribute(value = "fromdate")
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	@DynamoDbAttribute(value = "todate")
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	@DynamoDbAttribute(value = "mealtype")
	public String getMealtype() {
		return mealtype;
	}
	public void setMealtype(String mealtype) {
		this.mealtype = mealtype;
	}
	@Override
	public String toString() {
		return "FlightSchedule [flightName=" + flightName + ", airlineName=" + airlineName + ", scheduleId="
				+ scheduleId + ", source=" + source + ", destination=" + destination + ", fromdate=" + fromdate
				+ ", todate=" + todate + ", mealtype=" + mealtype + "]";
	}
	public FlightSchedule(String flightName, String airlineName, String scheduleId, String source, String destination,
			String fromdate, String todate, String mealtype) {
		super();
		this.flightName = flightName;
		this.airlineName = airlineName;
		this.scheduleId = scheduleId;
		this.source = source;
		this.destination = destination;
		this.fromdate = fromdate;
		this.todate = todate;
		this.mealtype = mealtype;
	}
	
	public FlightSchedule() {}
}
