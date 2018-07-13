package ar.com.fjs.validation.impl;

import ar.com.fjs.validation.Validacion;

public class ValidarLongitudNombreObjeto implements Validacion {
	private String target;
	
	public ValidarLongitudNombreObjeto(String target) {
		this.target = target;
	}
	
	public boolean validar() {
		return this.target.length() <= 30;
	}

}
