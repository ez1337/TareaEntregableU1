package com.carballeira.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientModel implements Serializable{
	private final String id;
	private ArrayList<BookingModel> bookingHistory = new ArrayList<>();
	final long serialVersionUID = Long.parseLong("-3119844876411586590");
	
	public ClientModel(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setBookingHistory(BookingModel booking) {
		bookingHistory.add(booking);
	}
	
	public ArrayList<BookingModel> getBookingHistory(){
		return bookingHistory;
	}
}

