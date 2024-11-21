package com.carballeira.utils;

import java.util.Random;

public class GenerateIDUtil {
	
	/* Método que genera código único de identificacion de reserva */
	public static String generateUniqueID() { 
		Random random = new Random(); 
		// Letras fijas "EF" 
		String letters = "EF"; 
		// Generar tres números aleatorios entre 001 y 999 
		int number = 1 + random.nextInt(999); 
		// Formatear el número a tres dígitos 
		String formattedNumber = String.format("%03d", number); 
		// Combinar las letras fijas y los números 
		return letters + formattedNumber; 
	}

}
