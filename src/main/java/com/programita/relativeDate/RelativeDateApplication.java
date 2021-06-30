package com.programita.relativeDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
		askForDate();
	}

	public static void askForDate() {
		Scanner in = new Scanner(System.in);

		System.out.println("Ingresa el dia: ");
		String dia = in.nextLine();
		System.out.println("Ingresa el mes: ");
		String mes = in.nextLine();
		System.out.println("Ingresa el anio: ");
		String anio = in.nextLine();
		System.out.println("Ingresa la hora: ");
		String hora = in.nextLine();
		System.out.println("Ingresa el minuto: ");
		String minuto = in.nextLine();

		LocalDateTime date = LocalDateTime.of(
				Integer.valueOf(anio), Integer.valueOf(mes),
				Integer.valueOf(dia), Integer.valueOf(hora),
				Integer.valueOf(minuto));
		try {
			readFiles(date);
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void readFiles(LocalDateTime initialDate)

			throws JsonIOException, IOException {
		File folder = new File(
				"C:\\Users\\Usuario\\Desktop\\New folder");
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				BufferedReader br = new BufferedReader(
						new FileReader(file));
				JsonReader read = new JsonReader(br);
				SimulationData data = gson.fromJson(read,
						SimulationData.class);
				data.getSetupEscenario()
						.setFechaInicio(initialDate.format(
								DateTimeFormatter.ofPattern(
										"yyyy-MM-dd HH:mm:ss")));

				data.ordenarEventos();
				for (int i = 0; i < data.getEventos()
						.size(); i++) {
					Evento evento = data.getEventos()
							.get(i);
					initialDate = initialDate
							.plusDays(Long.valueOf(evento
									.getDiaRespectoInicio()));

					LocalDateTime staticDate = initialDate
							.plusMinutes(i * 2 + 10);

					String fechaFormateada = staticDate
							.format(DateTimeFormatter
									.ofPattern(
											"yyyy-MM-dd HH:mm:ss"));
					evento.setFecha(fechaFormateada);
					if (evento.getTipoEvento()
							.equals("Crear Comunidad")) {
						evento.getPayload()
								.setFechaFinalizacionComunidad(
										staticDate
												.plusMinutes(
														1)
												.format(DateTimeFormatter
														.ofPattern(
																"yyyy-MM-dd HH:mm:ss")));
					}
					if (i == data.getEventos().size() - 1) {
						initialDate = initialDate.plusDays(
								Long.valueOf(evento
										.getDiaRespectoInicio()));
					} else {
						initialDate = initialDate.minusDays(
								Long.valueOf(evento
										.getDiaRespectoInicio()));
					}

				}
				Writer writer = new FileWriter(
						"C:\\Users\\Usuario\\Desktop\\New folder\\"
								+ file.getName().replace(
										"_base",
										"_plantilla"));
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
