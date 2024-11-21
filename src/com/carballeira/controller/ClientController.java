package com.carballeira.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.carballeira.model.BookingModel;
import com.carballeira.model.ClientModel;
import com.carballeira.utils.AlertUtil;
import com.carballeira.view.ClientView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ClientController {
	
	// CONSTANTS
	private final String CLIENTS_PATH = "Clients/clients.bin";
	
	// COMPONENTS
	private ClientView view;
	private ClientModel model;
	
	public ClientController(ClientModel model, ClientView view) {
		super();
		this.view = view;
		this.model = model;
	}
	
	public ClientController(ClientView view) {
		super();
		this.view = view;
	}
	
	
	/* Método que añade el historial de reservas a la tabla de reservas hechas */
	public void addBookingData(ClientModel client) {
		ObservableList<BookingModel> bookingData = FXCollections.observableArrayList(client.getBookingHistory());
		
		view.updateTable(bookingData);	
	}
	
	
	/* Método que borra una reserva seleccionada del TableView de reservas */
	public void deleteBooking() {
		BookingModel booking = view.getSelectedBookingFromTable();
		String WARNING_SMS = "¿Quiere eliminar la reserva con ID "+booking.getBookingId()+"?";
		new AlertUtil().informativeAlert(WARNING_SMS);
		
		if (booking != null) {
            String OK_SMS = "Reserva eliminada con éxito";
            new AlertUtil().informativeAlert(OK_SMS);
			
			model.getBookingHistory().remove(booking); 
            view.removeSelectedBookingFromTable(booking); 
        } else {
            String SMS = "No hay ninguna reserva seleccionada para eliminar.";
        	new AlertUtil().informativeAlert(SMS);
        }
	}
	
	/* Metodo que lee desde fichero los clientesy  los añade a la tableview de la vista ClientView */
	public void loadClients() {
	
	    System.out.println("Importando clientes...");
	    File clients = new File(CLIENTS_PATH);
	
	    try(FileInputStream fis = new FileInputStream(clients);
	        BufferedInputStream bufis = new BufferedInputStream(fis);
	        ObjectInputStream read = new ObjectInputStream(fis)){
	
	        while(fis.available()>0){
	            ClientModel client = (ClientModel) read.readObject();
	            view.addHistoryTable(client);
	        }
	
	    } catch(IOException | ClassNotFoundException e){
	        System.err.println(" ERROR : " + e);
	    }
	}
	
	
	/* Metodo que crea y/o actualiza el fichero de clientes. Reescribe el fichero preexistente.*/
	public void exportClients() {
		
		System.out.println("Actualizando clientes...");
		File file = new File(CLIENTS_PATH);
	    try(FileOutputStream fos = new FileOutputStream(file,true);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        ObjectOutputStream oos = new ObjectOutputStream(bos) ){

	        oos.writeObject(model);
	
	    } catch(IOException e){
	        System.err.println("ERROR: "+e);
	    }
	}
}


