package ar.com.fjs.validation.impl;

import ar.com.fjs.validation.Validacion;

public class ValidarLongitudNombreObjeto implements Validacion {
	private String clausula; 
	private String sql;
	private String target;
	
	public ValidarLongitudNombreObjeto(String clausula, String sql, String target) {
		super();
		this.clausula = clausula;
		this.sql = sql;
		this.target = target;
	}
	
	public boolean validar() {
		return this.target.length() <= 30;
	}

}
