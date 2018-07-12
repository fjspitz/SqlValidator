package ar.com.fjs.model;

import ar.com.fjs.validation.Validacion;
import ar.com.fjs.validation.impl.ValidarUsoDeClausula;

public class Regla {
	private int id;
	private String nombre;
	private String descripcion;
	private Validacion validacion;
	private boolean estado;
	
	public Regla(int id, String nombre, String descripcion, Validacion validacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.validacion = validacion;
		this.estado = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Validacion getValidacion() {
		return validacion;
	}

	public void setValidacion(Validacion validacion) {
		this.validacion = validacion;
	}

	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		if (getValidacion() instanceof ValidarUsoDeClausula) {
			return String.format("[%s] %d. %s: %s (posición: %d)", (getEstado()) ? "OK " : "ERR",getId(), getNombre(), getDescripcion(), ((ValidarUsoDeClausula)getValidacion()).getPosicion());
		} else {
			return String.format("[%s] %d. %s: %s", (getEstado()) ? "OK " : "ERR", getId(), getNombre(), getDescripcion());
		}
	}
	
}
