module Prueba3_Jdk21 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	
	opens com.carballeira.view to javafx.graphics, javafx.fxml;
	opens com.carballeira.model to javafx.graphics, javafx.fxml, javafx.base;
}
