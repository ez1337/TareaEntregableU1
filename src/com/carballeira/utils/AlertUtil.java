package com.carballeira.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertUtil {
	public void informativeAlert(String sms) {
		Alert info = new Alert(AlertType.INFORMATION);
		info.setTitle("AVISO");
		info.setContentText(sms);
		info.showAndWait();
	}
}
