package com.programita.relativeDate.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class Payload {
	private ArrayList<RespuestaPregunta> respuestasPreguntas;
	private Tamizaje tamizaje;
	private ArrayList<String> idPersonas;
	private String idComunidad;
	private String horaIngreso;
	private ArrayList<String> integrantes;
	private String idPersona;
	private String descripcionComunidad;
	private String estadoEsperado;
	private String tipoCargaFile;
	private String fechaFinalizacionComunidad;
	private ArrayList<Persona> personasCercadas = new ArrayList<>();

	public String getRepuestasPreguntasFormated() {
		String respuestasPreguntas = this.respuestasPreguntas.stream()
				.map(m -> "Pregunta #" + m.getPreguntaId() + ":" + m.getRespuesta()).collect(Collectors.toList())
				.toString();

		return respuestasPreguntas;
	}

	public String getFormatoFechaFinalizacion() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(fechaFinalizacionComunidad, formatter);
		String dia = String.valueOf(dateTime.getDayOfMonth());
		String mes = String.valueOf(dateTime.getMonth().getValue());
		String year = String.valueOf(dateTime.getYear());
		String hora = String.valueOf(dateTime.getHour());
		return String.format("%s-%s-%s %s:00:00", year, mes, dia, hora);
	}

	public String generateInformation(String tipoEvento) {
		String resp = "";
		switch (tipoEvento) {
		case "Responder Encuesta Nexos":
			resp = getRepuestasPreguntasFormated();
			break;
		case "Responder Encuesta Sintomas":
			resp = getRepuestasPreguntasFormated();
			break;
		case "Realizar Tamizaje":
			resp = "Se simulara un tamizaje con la temperatura: " + this.tamizaje.getTemperatura();
			break;
		case "Crear Comunidad":
			resp = "Se simulara la creacion de una comunidad";
			break;
		case "Cargar resultado examen":
			resp = "Se simulara una carga de prueba " + (this.tipoCargaFile.equals("Y") ? "positiva" : "negativa")
					+ "de covid";
			break;
		case "Obtener cercos epidemiologicos":
			resp = "";
			break;
		case "Obtener estados personas":
			resp = "";
			break;
		case "Asistir a comunidad":
			resp = "";
			break;
		default:
			resp = "";
			break;
		}

		return resp;
	}

}