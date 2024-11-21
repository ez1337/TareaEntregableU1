package com.carballeira.controller;

import com.carballeira.model.BookingModel;
import com.carballeira.model.ClientModel;
import com.carballeira.utils.AlertUtil;
import com.carballeira.view.BookingView;
import com.carballeira.view.ClientView;

import javafx.stage.Stage;

public class BookingController {
	private BookingView view;
	private BookingModel model;
	
	public BookingController(BookingView view, BookingModel model) {
		super();
		this.view = view;
		this.model = model;
		
	}
	
	
	/* Comprueba que la reserva de vuelo es v�lida para a�adir al historial de reservas
	 * que se pasa hacia la vista de cliente.*/
	public boolean checkValidFlight() {
		if(view.getSelectedFlightFromTable() == null) {
			String SMS = "Seleccione un vuelo para reservar.";
			new AlertUtil().informativeAlert(SMS);
			return false;
		}
		else if(model.emptySeats()) {
			String SMS = "Ingrese un n�mero v�lido de asientos para reservar.";
			new AlertUtil().informativeAlert(SMS);
			return false;
		}
		else if(model.validNumOfSeats() && model.getFlight().checkStatus() && !model.emptySeats()) {
			String SMS = "�Vuelo reservado con �xito!";		
			new AlertUtil().informativeAlert(SMS);
			return true;
		}
		else {
			String SMS = "El vuelo no est� disponible o ha reservado m�s de 10 asientos";
			new AlertUtil().informativeAlert(SMS);
		}
		return false;
	}
	
	
	/* M�todo que crea un ClientView y su controlador para a�adir las reservas 
	 * hechas en BookingView hacia la tabla de historial de reservas */
	public void addBookingHistory(ClientModel client, Stage primaryStage) {
		try {
            ClientView clientView = new ClientView(primaryStage, client);
            ClientController clicontrol = new ClientController(client, clientView);
            
            clicontrol.addBookingData(client);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
}


