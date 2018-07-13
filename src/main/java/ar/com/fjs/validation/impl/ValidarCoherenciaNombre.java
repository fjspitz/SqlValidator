package ar.com.fjs.validation.impl;

import ar.com.fjs.validation.Validacion;

/**
 * Se valida que el nombre del archivo coincida con el nombre utilizado dentro del script
 * para el tratamiento del objeto.
 * 
 * Se pretende evitar enviar a catalogar un script cuyo nombre refiere a un objeto A, pero 
 * cuyo contenido hace referencia a operaciones sobre el objeto B.
 * 
 * @author fernando
 *
 */
public class ValidarCoherenciaNombre implements Validacion {
	
	public boolean validar() {
		// TODO Auto-generated method stub
		return false;
	}

}
