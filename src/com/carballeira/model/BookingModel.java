package com.carballeira.model;

public class BookingModel{
	private String bookingId;
	private int seats;
	private boolean active;
	private FlightModel flight;

	
	public BookingModel(String bookingId, int seats, boolean active, FlightModel flight) {
		super();
		this.bookingId = bookingId;
		this.seats = seats;
		this.active = active;
		this.flight = flight;
	}

	public boolean validNumOfSeats() {
		if(seats <= 10) {
			return true;
		}
		return false;
	}
	
	public boolean emptySeats() {
		if(seats <= 0) {
			return true;
		}
		return false;
	}

	public String getBookingId() {
		return bookingId;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public FlightModel getFlight() {
		return flight;
	}

	public void setFlight(FlightModel flight) {
		this.flight = flight;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	@Override
	public String toString() {
		return "bookingId=" + bookingId + ", seats=" + seats + ",flight=" + flight.getFlightNum();
	}

	
	
}
