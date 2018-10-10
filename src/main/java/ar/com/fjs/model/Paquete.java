package ar.com.fjs.model;

import java.util.ArrayList;
import java.util.List;

public class Paquete {
	private String nombre;
	private List<Regla> reglas;
	
	public Paquete() {
		reglas = new ArrayList<>();
	}

	public Paquete(String nombre, List<Regla> reglas) {
		this.nombre = nombre;
		this.reglas = reglas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Regla> getReglas() {
		return reglas;
	}

	public void setReglas(List<Regla> reglas) {
		this.reglas = reglas;
	}
	
	public void agregarRegla(Regla regla) {
		this.reglas.add(regla);
	}
}
