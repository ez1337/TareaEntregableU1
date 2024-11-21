package com.carballeira.utils;

import com.carballeira.controller.ClientController;
import com.carballeira.model.ClientModel;
import com.carballeira.view.BookingView;
import com.carballeira.view.ClientView;

import javafx.stage.Stage;

public class ChangeViewUtil {
	private Stage primaryStage;

	public ChangeViewUtil(Stage primaryStage) {
		super();
		this.primaryStage = primaryStage;
	}

	
	/* Muestra la vista ClientView sin pasar por parámetro el historial de reservas */
	public void changeToEmptyClientView() {
		try {
            ClientView clientView = new ClientView(primaryStage);
            clientView.show(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	/* Muestra el ClientView con el historial de reservas */
	public void changeToClientView(ClientModel client) {
		try {
            ClientView clientView = new ClientView(primaryStage, client);
            ClientController clicontrol = new ClientController(client, clientView);
            
            clicontrol.addBookingData(client);
            
            clientView.show(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	/* Cambia hacia BookingView junto con el historial de reservas */
	public void changeToBookingView(ClientModel client) {
		BookingView main = new BookingView();
		main.receiveClient(client);
		main.start(primaryStage);
	}
}
