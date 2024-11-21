package com.carballeira.model;

import java.io.Serializable;

public class FlightModel implements Serializable{
	private String flightNum;
	private String departure;
	private String arrival;
	private String depDate;
	private String arrDate;
	private String flightClass;
	private int seats;
	private char status; //A: available, C: cancelled, F: full
	final long serialVersionUID = Long.parseLong("-3119844876411586590");
	
	public FlightModel(String flightNum, String departure, String arrival, String depDate, String arrDate,
			String flightClass, int seats, char status) {
		super();
		this.flightNum = flightNum;
		this.departure = departure;
		this.arrival = arrival;
		this.depDate = depDate;
		this.arrDate = arrDate;
		this.flightClass = flightClass;
		this.seats = seats;
		this.status = status;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	public String getArrDate() {
		return arrDate;
	}

	public void setArrDate(String arrDate) {
		this.arrDate = arrDate;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	public void updateStatus() {
		if(seats == 0) {
			status = 'F';
		}
	}
	
	public boolean checkStatus() {
		if(status == 'A') {
			return true;
		}
		return false;
	}
}
