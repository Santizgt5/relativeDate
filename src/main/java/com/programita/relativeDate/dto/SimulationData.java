package com.programita.relativeDate.dto;

import java.util.ArrayList;
import java.util.Collections;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimulationData {

	public SetupEscenario setupEscenario;
	public ArrayList<Persona> personas;
	public Comunidades comunidades;
	public ArrayList<Evento> eventos = new ArrayList<>();
	public ArrayList<AssertFinal> assertsFinales;

	public void ordenarEventos() {
		Collections.sort(eventos);
	}
	
}
