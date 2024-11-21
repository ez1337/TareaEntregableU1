package com.carballeira.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.carballeira.model.FlightModel;
import com.carballeira.view.BookingView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class FlightController {
	private BookingView view;
	private ArrayList<FlightModel> originalFlights = new ArrayList<>();
	private final String FLIGHTS_PATH = "Flights/flights.bin";
	
	public FlightController(BookingView view) {
		super();
		this.view = view;
	}
	
	
	/* Metodo que lee desde fichero los objetos vuelo y los añade a la tableview de la vista BookingView */
	public void loadFlights() {
	
	    System.out.println("Importando vuelos...");
	    File clients = new File(FLIGHTS_PATH);
	
	    try(FileInputStream fis = new FileInputStream(clients);
	        BufferedInputStream bufis = new BufferedInputStream(fis);
	        ObjectInputStream read = new ObjectInputStream(fis)){
	
	        while(fis.available()>0){
	            FlightModel flight = (FlightModel) read.readObject();
	            view.addFlightsTable(flight);
	        }
	
	    } catch(IOException | ClassNotFoundException e){
	        System.err.println(" ERROR : " + e);
	    }
	}
	
	
	/* Metodo que crea y/o actualiza el fichero de vuelos. Reescribe el fichero preexistente.*/
	public void exportFlights() {
		
		System.out.println("Actualizando vuelos...");
		File file = new File(FLIGHTS_PATH);
	    try(FileOutputStream fos = new FileOutputStream(file,true);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        ObjectOutputStream oos = new ObjectOutputStream(bos) ){
	
	        for(FlightModel fm : view.getFlightList()){
	            oos.writeObject(fm);
	        }
	
	    } catch(IOException e){
	        System.err.println("ERROR: "+e);
	    }
	}
	
	public void clearComboBoxAndResetTable() { 
		// Limpiar los ComboBox
		view.clearComboBoxes();
		
		// Restablecer la lista original de vuelos en el TableView 
		for(FlightModel fm : originalFlights) {
			view.addFlightsTable(fm);
		}
	}
	
	/* Los metodos ToComboBox() recorren la lista de vuelos y añaden un listado de elementos únicos a cada ComboBox */
	public void addDeparturesToComboBox() { 
		List<String> uniqueDepartures = new ArrayList<>(); 
		for (FlightModel flight : view.getFlightList()) {
			boolean isUnique = true; 
			for (String departure : uniqueDepartures) { 
				if (departure.equals(flight.getDeparture())) { 
					isUnique = false; 
					break; } 
				} 
			if (isUnique) { 
				uniqueDepartures.add(flight.getDeparture()); 
				} 
			} 
		ObservableList<String> departureList = FXCollections.observableArrayList(uniqueDepartures); 
		view.getDepartureCombo().setItems(departureList);
	}
	
	public void addArrivalsToComboBox() { 
		List<String> uniqueArrivals = new ArrayList<>(); 
		for (FlightModel flight : view.getFlightList()) {
			boolean isUnique = true; 
			for (String arrival : uniqueArrivals) { 
				if (arrival.equals(flight.getArrival())) { 
					isUnique = false; 
					break; } 
				} 
			if (isUnique) { 
				uniqueArrivals.add(flight.getArrival()); 
				} 
			} 
		ObservableList<String> arrivalsList = FXCollections.observableArrayList(uniqueArrivals); 
		view.getArrivalCombo().setItems(arrivalsList);
	}
	
	
	public void addDateDepartureToComboBox() { 
		List<String> uniqueDateDeparture = new ArrayList<>(); 
		for (FlightModel flight : view.getFlightList()) {
			boolean isUnique = true; 
			for (String dateDep : uniqueDateDeparture) { 
				if (dateDep.equals(flight.getDepDate())) { 
					isUnique = false; 
					break; } 
				} 
			if (isUnique) { 
				uniqueDateDeparture.add(flight.getDepDate()); 
				} 
			} 
		ObservableList<String> departureDateList = FXCollections.observableArrayList(uniqueDateDeparture); 
		view.getDepDateCombo().setItems(departureDateList);
	}
	
	public void addDateArrivalsToComboBox() { 
		List<String> uniqueDateArrivals = new ArrayList<>(); 
		for (FlightModel flight : view.getFlightList()) {
			boolean isUnique = true; 
			for (String dateArr : uniqueDateArrivals) { 
				if (dateArr.equals(flight.getArrDate())) { 
					isUnique = false; 
					break; } 
				} 
			if (isUnique) { 
				uniqueDateArrivals.add(flight.getArrDate()); 
				} 
			} 
		ObservableList<String> arrivalsDateList = FXCollections.observableArrayList(uniqueDateArrivals); 
		view.getArrDateCombo().setItems(arrivalsDateList);
	}
	
	public void addClassToComboBox() { 
		view.getClassCombo().getItems().add("Economy");
		view.getClassCombo().getItems().add("Business");
		view.getClassCombo().getItems().add("First");
	}
	
	
	/* Los metodos filterBy a�aden un listener a cada ComboBox, crea una lista filtrada según el elemento seleccionado en el
	 * ComboBox y muestra los elementos filtrados en la tableview de vuelos*/
	public void filterFlightsByDestination() { 
		// Crear la lista filtrada 
		FilteredList<FlightModel> filteredFlights = new FilteredList<>(view.getFlightList(), p -> true); 
		
		// Actualizar la lista filtrada cuando se selecciona un destino 
		view.getArrivalCombo().valueProperty().addListener((observable, oldValue, newValue) -> { 
			filteredFlights.setPredicate(flight -> { 
				if (newValue == null || ((String) newValue).isEmpty()) { 
					return true; 
					}
		return flight.getArrival().equals(newValue); 
		}); 
		view.getTableView().setItems(filteredFlights); 
		});
	}
	
	public void filterFlightsByOrigin() { 
		// Crear la lista filtrada 
		FilteredList<FlightModel> filteredFlights = new FilteredList<>(view.getFlightList(), p -> true); 
		
		// Actualizar la lista filtrada cuando se selecciona un destino 
		view.getDepartureCombo().valueProperty().addListener((observable, oldValue, newValue) -> { 
			filteredFlights.setPredicate(flight -> { 
				if (newValue == null || ((String) newValue).isEmpty()) { 
					return true; 
					}
		return flight.getDeparture().equals(newValue); 
		}); 
		view.getTableView().setItems(filteredFlights); 
		});
	}
	
	public void filterFlightsByArrivalDate() { 
		// Crear la lista filtrada 
		FilteredList<FlightModel> filteredFlights = new FilteredList<>(view.getFlightList(), p -> true); 
		
		// Actualizar la lista filtrada cuando se selecciona un destino 
		view.getArrDateCombo().valueProperty().addListener((observable, oldValue, newValue) -> { 
			filteredFlights.setPredicate(flight -> { 
				if (newValue == null || ((String) newValue).isEmpty()) { 
					return true; 
					}
		return flight.getArrDate().equals(newValue); 
		}); 
		view.getTableView().setItems(filteredFlights); 
		});
	}
	
	public void filterFlightsByDepartureDate() { 
		// Crear la lista filtrada 
		FilteredList<FlightModel> filteredFlights = new FilteredList<>(view.getFlightList(), p -> true); 
		
		// Actualizar la lista filtrada cuando se selecciona un destino 
		view.getDepDateCombo().valueProperty().addListener((observable, oldValue, newValue) -> { 
			filteredFlights.setPredicate(flight -> { 
				if (newValue == null || ((String) newValue).isEmpty()) { 
					return true; 
					}
		return flight.getDepDate().equals(newValue); 
		}); 
		view.getTableView().setItems(filteredFlights); 
		});
	}
	
	public void filterFlightsByClass() { 
		// Crear la lista filtrada 
		FilteredList<FlightModel> filteredFlights = new FilteredList<>(view.getFlightList(), p -> true); 
		
		// Actualizar la lista filtrada cuando se selecciona un destino 
		view.getClassCombo().valueProperty().addListener((observable, oldValue, newValue) -> { 
			filteredFlights.setPredicate(flight -> { 
				if (newValue == null || ((String) newValue).isEmpty()) { 
					return true; 
					}
		return flight.getFlightClass().equals(newValue); 
		}); 
		view.getTableView().setItems(filteredFlights); 
		});
	}

}




