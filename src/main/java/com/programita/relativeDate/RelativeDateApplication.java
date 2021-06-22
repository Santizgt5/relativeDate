package com.programita.relativeDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;
import com.programita.relativeDate.dto.Evento;
import com.programita.relativeDate.dto.SimulationData;

@SpringBootApplication
public class RelativeDateApplication {

	private static final Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.setDateFormat("dd-MM-yyyy HH:mm:ss").create();

	public static void main(String[] args)
			throws JsonIOException, IOException {
		SpringApplication.run(RelativeDateApplication.class,
				args);

		readFiles();

	}

	public static void readFiles()
			throws JsonIOException, IOException {
		File folder = new File(
				"C:\\Users\\Usuario\\Desktop\\New folder");
		File[] listOfFiles = folder.listFiles();

		LocalDateTime fecha = LocalDateTime.now();
		for (File file : listOfFiles) {
			if (file.isFile()) {
				BufferedReader br = new BufferedReader(
						new FileReader(file));
				JsonReader read = new JsonReader(br);
				SimulationData data = gson.fromJson(read,
						SimulationData.class);
				data.getSetupEscenario()
						.setFechaInicio(fecha.format(
								DateTimeFormatter.ofPattern(
										"yyyy-MM-dd HH:mm:ss")));
				data.ordenarEventos();
				for (int i = 0; i < data.getEventos()
						.size(); i++) {
					Evento evento = data.getEventos()
							.get(i);
					fecha = fecha.plusDays(Long.valueOf(
							evento.getDiaRespectoInicio()));
					String fechaFormateada = fecha.format(
							DateTimeFormatter.ofPattern(
									"yyyy-MM-dd HH:mm:ss"));
					evento.setFecha(fechaFormateada);
					if (i == data.getEventos().size() - 1) {
						fecha = fecha.plusDays(Long.valueOf(
								evento.getDiaRespectoInicio()));
					} else {
						fecha = fecha.minusDays(
								Long.valueOf(evento
										.getDiaRespectoInicio()));
					}

				}

//				for (Evento evento : data.getEventos()) {
//					fecha = fecha.plusDays(Long.valueOf(
//							evento.getDiaRespectoInicio()));
//					String fechaFormateada = fecha.format(
//							DateTimeFormatter.ofPattern(
//									"yyyy-MM-dd HH:mm:ss"));
//					evento.setFecha(fechaFormateada);
//					fecha = fecha.minusDays(Long.valueOf(
//							evento.getDiaRespectoInicio()));
//
//				}
				Writer writer = new FileWriter(
						"C:\\Users\\Usuario\\Desktop\\New folder\\"
								+ new Random().nextDouble()
								+ ".json");
				gson.toJson(data, writer);
				writer.flush();
				writer.close();
				System.out.println("Nuevo caso");
				data.getEventos().forEach(l -> System.out
						.println(l.getFecha()));

			}
		}

	}

}
