package com.carballeira.view;

import com.carballeira.controller.BookingController;
import com.carballeira.controller.FlightController;
import com.carballeira.model.BookingModel;
import com.carballeira.model.ClientModel;
import com.carballeira.model.FlightModel;
import com.carballeira.utils.ChangeViewUtil;
import com.carballeira.utils.GenerateIDUtil;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BookingView extends Application{
	
	// DECLARACION DE COMPONENTES
	private TableView<FlightModel> tableView;
	private ComboBox departureCombo;
	private ComboBox arrivalCombo;
	private ComboBox depDateCombo;
	private ComboBox arrDateCombo;
	private ComboBox classCombo;
	private Button bookBtn;
	private Button resetBtn;
	private Button userBtn;
	private Label lblDeparture;
	private Label lblArrival;
	private Label lbldepDate;
	private Label lblarrDate;
	private Label lblClass;
	private Label lblSeats;
	private TextField numSeats;
	private FlightController fcontrol;
	private BookingController bcontrol;
	
	/* Array de clientes que se usa como mensajero para enviar a ClientView el historial de reservas */
	private ClientModel[] clients = new ClientModel[1];
	
	// DECLARACION DE CONSTANTES
	private final String PATH_CSS = "/application/application.css";
	private final String TITLE = "EuroFly";
	private final String DEPARTURE = "Origen:";
	private final String ARRIVAL = "Destino:";
	private final String DEPARTURE_DATE = "Fecha ida:";
	private final String ARRIVAL_DATE = "Fecha llegada:";
	private final String CLASS = "Clase:";
	private final String CLIENT_VIEW = "Area cliente";
	private final String BOOK_FLIGHT = "Reservar";
	private final String RESTORE_FLIGHT = "Restaurar lista";
	private final String NUM_SEATS = "Asientos a reservar";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			bookingScene(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public GridPane bookingScene(Stage primaryStage) {
		GridPane root = new GridPane();
		Scene scene = new Scene(root,1600,600);
		
		root.add(navbar(TITLE), 0, 0);
		root.add(drawStack0(), 0, 1);
		root.add(drawTable(), 0, 2);
		root.add(rowButtons(primaryStage), 0, 3);

		/* Carga de la lista de vuelos desde fichero. */
		fcontrol = new FlightController(this);
		fcontrol.loadFlights();
//        fcontrol.exportFlights();
        
        
		this.addElementsToCombo(fcontrol);
		this.filterFlights(fcontrol);
		
		scene.getStylesheets().add(getClass().getResource(PATH_CSS).toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.show();
		return root;
		
	}
	
	public GridPane navbar(String TITLE) {
		GridPane navbar = new GridPane(); 
		Label title = new Label(TITLE);
        navbar.getStyleClass().add("navbar"); 
        navbar.add(title, 0, 1);
        return navbar;
	}
	
	public HBox rowButtons(Stage primaryStage) {
		Button bookBtn = bookBtn(primaryStage);
		Button userBtn = userBtn(primaryStage);
		Button resetBtn = resetBtn();
		
		HBox rowButtons = new HBox(bookBtn, userBtn, resetBtn);
		rowButtons.getStyleClass().add("rowButtons"); 
		return rowButtons;
	}
	
	public void receiveClient(ClientModel client) {
		clients[0] = client;
	}
	
	public void filterFlights(FlightController fcontrol) {
		fcontrol.filterFlightsByDestination();
		fcontrol.filterFlightsByOrigin();
		fcontrol.filterFlightsByArrivalDate();
		fcontrol.filterFlightsByDepartureDate();
		fcontrol.filterFlightsByClass();
	}
	
	public void addElementsToCombo(FlightController fcontrol) {
		fcontrol.addDeparturesToComboBox();
		fcontrol.addArrivalsToComboBox();
		fcontrol.addDateArrivalsToComboBox();
		fcontrol.addDateDepartureToComboBox();
		fcontrol.addClassToComboBox();
	}
	
	/* Metodo que dibuja la fila de listas desplegables y sus etiquetas */
	public HBox drawStack0() {
		lblDeparture = new Label(DEPARTURE);
		departureCombo = new ComboBox();	
		lblArrival = new Label(ARRIVAL);
		arrivalCombo = new ComboBox();
		lbldepDate = new Label(DEPARTURE_DATE);
		depDateCombo = new ComboBox();
		lblarrDate = new Label(ARRIVAL_DATE);
		arrDateCombo = new ComboBox();
		lblClass = new Label(CLASS);
		classCombo = new ComboBox();
		lblSeats = new Label(NUM_SEATS);
		numSeats = new TextField();
		numSeats.setText("0");
		
		HBox stack0 = new HBox(15, lblDeparture, departureCombo, lblArrival, arrivalCombo, lbldepDate,
                depDateCombo, lblarrDate, arrDateCombo, lblClass, classCombo, lblSeats, numSeats);
		stack0.getStyleClass().add("form-container");

		return stack0;
	}
	
	/* Metodo que dibuja la tabla de vista de vuelos*/
	public TableView drawTable() {
		tableView = new TableView();
		
		TableColumn<FlightModel, String> colFlightNum = new TableColumn<>("Nombre");
    	colFlightNum.setCellValueFactory(new PropertyValueFactory<>("flightNum"));
    	
    	TableColumn<FlightModel, String> colDeparture = new TableColumn<>("Origen");
    	colDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
    	
    	TableColumn<FlightModel, String> colArrival = new TableColumn<>("Destino");
    	colArrival.setCellValueFactory(new PropertyValueFactory<>("arrival"));
    	
    	TableColumn<FlightModel, String> colDepDate = new TableColumn<>("Fecha Salida");
    	colDepDate.setCellValueFactory(new PropertyValueFactory<>("depDate"));
    	
    	TableColumn<FlightModel, String> colArrDate = new TableColumn<>("Fecha Llegada");
    	colArrDate.setCellValueFactory(new PropertyValueFactory<>("arrDate"));
    	
    	TableColumn<FlightModel, String> colFlightClass = new TableColumn<>("Clase del vuelo");
    	colFlightClass.setCellValueFactory(new PropertyValueFactory<>("flightClass"));
    	
    	TableColumn<FlightModel, String> colStatus = new TableColumn<>("Estado");
    	colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    	
    	TableColumn<FlightModel, String> colSeats = new TableColumn<>("Asientos disponibles");
    	colSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));
    	
    	tableView.getColumns().addAll(colFlightNum, colDeparture, colArrival, colDepDate, colArrDate, colFlightClass, colSeats, colStatus);
    	
    	tableView.setItems(FXCollections.observableArrayList());
		
		return tableView;
	}
	
	/* Boton que cambia la vista hacia la vista del cliente. Si no hay historial de reservas
	 * abre ClientView vac�o. Si hay historial de reservas, env�a el historial hacia ClientView*/
	public Button userBtn(Stage primaryStage) {
		userBtn = new Button(CLIENT_VIEW);
		userBtn.setOnAction(e -> {			
			if(clients[0] == null) {
				new ChangeViewUtil(primaryStage).changeToEmptyClientView();
			}
			else {
				new ChangeViewUtil(primaryStage).changeToClientView(clients[0]);					
			}
		});
		return userBtn;
	}
	
	public Button resetBtn() {
		resetBtn = new Button(RESTORE_FLIGHT);
		resetBtn.setOnAction(e -> {
			fcontrol.clearComboBoxAndResetTable();
		});
		return resetBtn;
	}
	
	/*Bot�n de hacer reserva. Comprueba si un vuelo es v�lido para reservar. Si no hay
	 * historial de reserva, crea un nuevo cliente que se usar� como historial. Si existe
	 * historial de reservas, a�ade nuevas reservas.*/
	public Button bookBtn(Stage primaryStage) {
		bookBtn = new Button(BOOK_FLIGHT);
		bookBtn.setOnAction(e -> {
			String id = GenerateIDUtil.generateUniqueID();
			int seats = Integer.parseInt(numSeats.getText());
			FlightModel flight = tableView.getSelectionModel().getSelectedItem();
			BookingModel booking = new BookingModel(id,seats,true,flight);
			
			bcontrol = new BookingController(this,booking);
			System.out.println(clients.length);
			if(bcontrol.checkValidFlight()) {
				if(clients[0] == null) {
					String idCli = "CUS001";
					ClientModel newClient = new ClientModel(idCli);				
					newClient.setBookingHistory(booking);
					clients[0] = newClient;
					bcontrol.addBookingHistory(newClient,primaryStage);												
				}
				else {
					clients[0].setBookingHistory(booking);
					bcontrol.addBookingHistory(clients[0],primaryStage);
				}
			}
		});
		return bookBtn;
	}
	
	/* Metodo que añade los datos del fichero hacia la tabla*/
	public void addFlightsTable(FlightModel flight) {
		tableView.getItems().add(flight);
	}

	
	/* Metodo que accede a la lista de objetos observables que contiene el tableview*/
	public ObservableList<FlightModel> getFlightList() {
		return tableView.getItems();
	}
	

	public TableView<FlightModel> getTableView(){
		return tableView;
	}
	
	public ComboBox getDepartureCombo() {
		return departureCombo;
	}


	public ComboBox getArrivalCombo() {
		return arrivalCombo;
	}


	public ComboBox getDepDateCombo() {
		return depDateCombo;
	}


	public ComboBox getArrDateCombo() {
		return arrDateCombo;
	}


	public ComboBox getClassCombo() {
		return classCombo;
	}
	
	public void clearComboBoxes() {
		// Limpiar los ComboBox
		getArrDateCombo().getSelectionModel().clearSelection();
		getDepDateCombo().getSelectionModel().clearSelection();
		getArrivalCombo().getSelectionModel().clearSelection();
		getDepartureCombo().getSelectionModel().clearSelection();
		getClassCombo().getSelectionModel().clearSelection();
	}
	
	public FlightModel getSelectedFlightFromTable() {
		return tableView.getSelectionModel().getSelectedItem();
	}

	public static void main(String[] args) {
		launch(args);
	}
}



