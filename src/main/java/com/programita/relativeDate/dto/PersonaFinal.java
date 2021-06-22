package com.programita.relativeDate.dto;

import lombok.Data;

@Data
public class PersonaFinal {
	
	String id;
	String name;
	String estadoActual;
	String estadoEsperado;
	
	
	public PersonaFinal(String id, String name, String estadoActual, String estadoEsperado) {
		this.id = id;
		this.name = name;
		this.estadoActual = estadoActual;
		this.estadoEsperado = estadoEsperado;
	}

}
