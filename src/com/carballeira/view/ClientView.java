package com.carballeira.view;

import com.carballeira.controller.ClientController;
import com.carballeira.model.BookingModel;
import com.carballeira.model.ClientModel;
import com.carballeira.model.FlightModel;
import com.carballeira.utils.AlertUtil;
import com.carballeira.utils.ChangeViewUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ClientView{
	
	// COMPONENTS
	private TableView<BookingModel> tableView;
	private Button cancelBtn;
	private Button returnBtn;
	private Stage stage;
	private ClientController clicontrol;
	private ClientModel[] clients = new ClientModel[1];
		
	// CONSTANTS
	private final String PATH_CSS = "/application/application.css";
	private final String CANCEL_FLIGHT = "Cancelar reserva";
	private final String BACK_TO_MAIN = "Regresar";
	private final String TITLE = "EuroFly/Customer";

	
	public ClientView(Stage primaryStage, ClientModel client) {
        this.stage = primaryStage;
        this.tableView = drawTable();
        this.clients[0] = client;
    }
	
	public ClientView(Stage primaryStage) {
		this.stage = primaryStage;
		this.tableView = drawTable();
	}
	
	public void show(Stage primaryStage) {
		try {
			
			 clientScene(primaryStage);
			 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateTable(ObservableList<BookingModel> bookingData) {

	    // Validar que el TableView no sea null
	    if (tableView != null) {
	        // Añadir las reservas al TableView
	        tableView.getItems().clear(); 
	        
	        tableView.setItems(bookingData);
	    } else {
	    	String SMS = "Tabla no inicializada";
			new AlertUtil().informativeAlert(SMS);
	    }	
	}
	
	public GridPane clientScene(Stage primaryStage) {
		GridPane root = new GridPane();
		Scene scene = new Scene(root,800,600);
		
		root.add(tableView, 0, 1);
		root.add(rowButtons(primaryStage), 0, 2);
		
		clicontrol = new ClientController(clients[0],this);
		clicontrol.loadClients();
		clicontrol.exportClients();
		
		scene.getStylesheets().add(getClass().getResource(PATH_CSS).toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.show();
		return root;
	}
	
	public HBox rowButtons(Stage primaryStage) {
		Button bookBtn = cancelBtn();
		Button userBtn = returnBtn(primaryStage);
		
		HBox rowButtons = new HBox(cancelBtn, returnBtn);
		rowButtons.getStyleClass().add("rowButtons"); 
		return rowButtons;
	}
	
	/* Método que dibuja la tabla de historial de reservas */
	public TableView drawTable() {
		tableView = new TableView<BookingModel>();
		
		TableColumn<BookingModel, String> colBookingID = new TableColumn<>("ID");
    	colBookingID.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
    	
    	TableColumn<BookingModel, String> colSeats = new TableColumn<>("Asientos reservados");
    	colSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));
    	
    	TableColumn<BookingModel, String> colFlightNum = new TableColumn<>("Número de vuelo");
    	colFlightNum.setCellValueFactory(cellData -> {
    	    // Obtener el objeto FlightModel del BookingModel
    	    FlightModel flight = cellData.getValue().getFlight();
    	    // Devolver un StringProperty con el número de vuelo
    	    return new SimpleStringProperty(flight != null ? flight.getFlightNum() : "No asignado");
    	});
    	
    	tableView.getColumns().addAll(colBookingID, colSeats, colFlightNum);
    	
    	tableView.setItems(FXCollections.observableArrayList());
		
		return tableView;
	}
	
	
	public TableView<BookingModel> getTableView(){
		return tableView;
	}
	
	public Button cancelBtn() {
		cancelBtn = new Button(CANCEL_FLIGHT);
		cancelBtn.setOnAction(e -> {
			clicontrol = new ClientController(clients[0],this);
			clicontrol.deleteBooking();
		});
		return cancelBtn;
	}
	
	public Button returnBtn(Stage primaryStage) {
		/* Botón que regresa a la vista principal */
		returnBtn = new Button(BACK_TO_MAIN);
		returnBtn.setOnAction(e -> {
			new ChangeViewUtil(primaryStage).changeToBookingView(clients[0]);
		});
		return returnBtn;
	}
	
	
	
	/* Metodo que añade los datos del fichero hacia la tabla*/
	public void addHistoryTable(ClientModel client) {
		for(BookingModel bm : client.getBookingHistory()) {
			tableView.getItems().add(bm);			
		}
	}
	
	public BookingModel getSelectedBookingFromTable() {
		return tableView.getSelectionModel().getSelectedItem();
	}
	
	public void removeSelectedBookingFromTable(BookingModel booking) {
		tableView.getItems().remove(booking);
	}

}




