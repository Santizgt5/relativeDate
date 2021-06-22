package com.programita.relativeDate.dto;


import java.util.ArrayList;
import java.util.Date;

import lombok.Data;

@Data
public class Comunidad {

	private String id;
	private int time;
	private int duracion;
	private String espacioFisico;
	private Date fechaInicio;
	private Date fechaFin;
	private ArrayList<Persona> personasInvolucradas;
}
