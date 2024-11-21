package com.carballeira.utils;

import java.util.Random;

public class GenerateIDUtil {
	
	/* M�todo que genera c�digo �nico de identificacion de reserva */
	public static String generateUniqueID() { 
		Random random = new Random(); 
		// Letras fijas "EF" 
		String letters = "EF"; 
		// Generar tres n�meros aleatorios entre 001 y 999 
		int number = 1 + random.nextInt(999); 
		// Formatear el n�mero a tres d�gitos 
		String formattedNumber = String.format("%03d", number); 
		// Combinar las letras fijas y los n�meros 
		return letters + formattedNumber; 
	}

}
