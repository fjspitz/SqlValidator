package ar.com.fjs.validation.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.fjs.validation.Validacion;

public class ValidarUsoDeClausula implements Validacion {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidarUsoDeClausula.class);
	private static final String ESQUEMA = "dbo.";
	private static final String COMILLA_SIMPLE = "'";
	private String clausula; 
	private String sql;
	private String target;
	private String mensajeError;
	private int posicion = -1;
	
	public ValidarUsoDeClausula(String sql, String clausula, String target) {
		this.sql = sql;
		this.clausula = clausula;
		this.target = target;
	}

	public boolean validar() {
		boolean result = false;
		LOGGER.debug("Buscando: {} {}", clausula.toLowerCase(), target.toLowerCase());
		
		if (sql.toLowerCase().indexOf(clausula.toLowerCase() + " " + target.toLowerCase()) > -1) {
			result = sql.toLowerCase().indexOf(clausula.toLowerCase() + " " + target.toLowerCase()) > -1;			
		} else if (sql.toLowerCase().indexOf(clausula.toLowerCase() + " " + ESQUEMA.concat(target.toLowerCase())) > -1) {
			LOGGER.debug("Buscando alternativa 1: {} {}", clausula.toLowerCase(), ESQUEMA.concat(target.toLowerCase()));
			result = sql.toLowerCase().indexOf(clausula.toLowerCase() + " " + ESQUEMA.concat(target.toLowerCase())) > -1;
		} else if (sql.toLowerCase().indexOf(clausula.toLowerCase() + " " + COMILLA_SIMPLE.concat(ESQUEMA).concat(target.toLowerCase().concat(COMILLA_SIMPLE))) > -1) {
			LOGGER.debug("Buscando alternativa 2: {} {}'", clausula.toLowerCase(), COMILLA_SIMPLE.concat(ESQUEMA).concat(target.toLowerCase().concat(COMILLA_SIMPLE)));
			result = sql.toLowerCase().indexOf(clausula.toLowerCase() + " " + COMILLA_SIMPLE.concat(ESQUEMA).concat(target.toLowerCase().concat(COMILLA_SIMPLE))) > -1;
		}
		
		if (!result) {
			setMensajeError("No se encontró la cláusula " + clausula + " " + target);
		} else {
			calcularPosicion();			
		}
		return result;
	}
	
	private void calcularPosicion() {
		
		this.posicion = sql.toLowerCase().indexOf(clausula.toLowerCase() + " " + target.toLowerCase());
	}
	
	public int getPosicion() {
		return this.posicion;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	@Override
	public String toString() {
		return mensajeError;
	}

}
