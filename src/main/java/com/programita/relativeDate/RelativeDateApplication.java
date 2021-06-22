package com.programita.relativeDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.programita.relativeDate.dto.Evento;
import com.programita.relativeDate.dto.SimulationData;

@SpringBootApplication
public class RelativeDateApplication {
	
	private static final Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();

	public static void main(String[] args) {
		SpringApplication.run(RelativeDateApplication.class, args);
		try {
			readFiles();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readFiles() throws FileNotFoundException {
		File folder = new File("C:\\Users\\Santiago Alvarez\\Desktop\\Pruebas");
		File[] listOfFiles = folder.listFiles();
		
		LocalDateTime fecha = LocalDateTime.now();
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		    	BufferedReader br = new BufferedReader(new FileReader(file));
		        JsonReader read = new JsonReader(br);
		        SimulationData data = gson.fromJson(read, SimulationData.class);
		        for(Evento chimbo : data.getEventos()) {
		        	System.out.println(chimbo.getDiaRespectoInicio());
		        	fecha = fecha.plusDays(Long.valueOf(chimbo.getDiaRespectoInicio()));
		        	String fechaFormateada = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		        	chimbo.setFecha(fechaFormateada);
		        	fecha = LocalDateTime.now();
		        }
		        System.out.println(data.getEventos());
		    }
		}
	}

}
