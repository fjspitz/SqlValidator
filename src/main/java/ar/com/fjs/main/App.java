package ar.com.fjs.main;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.fjs.model.Paquete;
import ar.com.fjs.model.Regla;
import ar.com.fjs.validation.Validacion;
import ar.com.fjs.validation.impl.ValidarLongitudNombreObjeto;
import ar.com.fjs.validation.impl.ValidarUsoDeClausula;

/**
 * Objetivo: parsear un archivo SQL encontrando ciertas cláusulas y en cierto orden para darlo por válido.
 * 
 * Darlo por válido implica que puede ser implementado en el ambiente correspondiente de manera automática.
 *
 */
public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	public static void main(String...args) {
		App app = new App();
		
		String databaseName = "PROF";
		
		for (String file : args) {
			LOGGER.info(String.format("Procesando: %s", file));
			String objectName = normalizeFilename(file);
			String statements = app.leer(file);
			
			Paquete paquete = cargarPaqueteDeReglas(statements, databaseName, objectName);			
			boolean resultado = app.process(statements, paquete);
			LOGGER.info(String.format("Resultado del proceso para %s: %s", file, resultado));
		}
	}
	
	private static String normalizeFilename(String filename) {
		return removePrefix(FilenameUtils.removeExtension(filename));
	}
	
	private static String removePrefix(String filename) {
		if (filename.toLowerCase().startsWith("dbo.")) {
			return filename.substring(filename.toLowerCase().indexOf("dbo.") + 4);
		} else {
			return filename;
		}
	}
	
	/**
	 * Carga el contenido de un archivo en memoria.
	 * 
	 * @param fileName
	 * @return
	 */
	
	public String leer(String fileName) {
		String result = "";
		
		ClassLoader classLoader = getClass().getClassLoader();
		try {
		    result = IOUtils.toString(classLoader.getResourceAsStream(fileName), "utf-8");
		} catch (IOException e) {
			LOGGER.error(String.format("Error al leer el archivo: %s", fileName));
		}
		return result;
	}
	
	/**
	 * Procesa un archivo aplicando el paquete de reglas que le haya sido definido.
	 * 
	 * @param statements
	 * @param paquete
	 * @return
	 */
	
	private boolean process(String statements, Paquete paquete) {
		boolean resultadoFinal = true;
		
		for (Regla regla : paquete.getReglas()) {
			regla.setEstado(regla.getValidacion().validar());
			LOGGER.info(regla.toString());
			if (!regla.getEstado()) {
				resultadoFinal = false;
			}
		}

		return resultadoFinal;
	}
	
	@SuppressWarnings("unused")
	private void printResult(Paquete paquete) {
		// DO stuff
		LOGGER.info(paquete.getNombre());
	}
	
	// TODO: esto debería ser cargado a partir de un archivo de configuración (properties, xml, etc).
	private static Paquete cargarPaqueteDeReglas(String statements, String databaseName, String objectName) {
		
		Validacion validarLongitudDeNombre = new ValidarLongitudNombreObjeto(objectName);
		Regla r1 = new Regla(1, "ValidarLongitudDeNombre", "Se valida que el nombre del objeto no supere el máximo permitido", validarLongitudDeNombre);
		
		Validacion validarUsoDeClausulaUse = new ValidarUsoDeClausula(statements, "use", databaseName);
		Regla r2 = new Regla(2, "ValidarClausulaUseDatabase", "Debe existir al inicio la instrucción USE con el nombre de la base de datos correspondiente", validarUsoDeClausulaUse);
		
		Validacion usoDeClausulaCreateSP = new ValidarUsoDeClausula(statements, "create procedure", objectName);
		Regla r3 = new Regla(3, "ValidarClausulaCreateProcedure", "Debe existir la instrucción de creación del objeto correspondiente", usoDeClausulaCreateSP);
		
		Validacion usoDeClausulaSPProxMode = new ValidarUsoDeClausula(statements, "sp_procxmode", objectName);
		Regla r4 = new Regla(4, "ValidarClausulaProcxmode", "Debe existir el llamado a sp_procxmode", usoDeClausulaSPProxMode);
		
		Validacion usoDeClausulaGrant = new ValidarUsoDeClausula(statements, "GRANT EXECUTE ON", objectName);
		Regla r5 = new Regla(5, "ValidarClausulaGrantExecute", "Debe existir el granteo con los permisos de ejecución para el objeto", usoDeClausulaGrant);
		
		Validacion usoDeClausulaDrop = new ValidarUsoDeClausula(statements, "drop procedure", objectName);
		Regla r6 = new Regla(6, "ValidarUsoClausulaDrop", "Debe ejecutarse el drop procedure siempre", usoDeClausulaDrop);
		
		Paquete paquete = new Paquete();
		paquete.agregarRegla(r1);
		paquete.agregarRegla(r2);
		paquete.agregarRegla(r3);
		paquete.agregarRegla(r4);
		paquete.agregarRegla(r5);
		paquete.agregarRegla(r6);
		
		return paquete;
	}
}
