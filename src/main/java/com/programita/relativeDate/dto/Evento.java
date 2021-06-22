package com.programita.relativeDate.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Evento implements Comparable<Evento> {

	private String tipoEvento;
	private String idPersona;

	private String fecha;

	private Payload payload;
	private int diaRespectoInicio;
	private ArrayList<Assert> asserts;

	public Evento() {
		this.asserts = new ArrayList<>();
	}

	@Override
	public int compareTo(Evento o) {
		return diaRespectoInicio - o.getDiaRespectoInicio();
	}

	public String getFormatoFecha() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(fecha,
				formatter);
		String dia = String
				.valueOf(dateTime.getDayOfMonth());
		String mes = String.valueOf(
				dateTime.getMonth().getValue() >= 10
						? dateTime.getMonth().getValue()
						: "0" + dateTime.getMonth()
								.getValue());
		String year = String
				.valueOf(dateTime.getYear() - 2000);
		String hora = String.valueOf(dateTime.getHour());
		return String.format("%s-%s-%s %s:00:00", dia, mes,
				year, hora);

	}

	public String getFormatoFechaPoll() {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(fecha,
				formatter);
		String dia = String
				.valueOf(dateTime.getDayOfMonth());
		String mes = String
				.valueOf(dateTime.getMonth().getValue());
		String year = String.valueOf(dateTime.getYear());
		String hora = String.valueOf(dateTime.getHour());
		return String.format("%s-%s-%s %s:00:00", year, mes,
				dia, hora);
	}
}