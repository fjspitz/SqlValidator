package ar.com.fjs.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.fjs.model.DatabaseElement;
import ar.com.fjs.model.Tipo;

public class FileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
	private static final String REGEX_DATABASE =  "(USE|use)\\s\\w*\\n(GO|go)";
	private static final String REGEX_SP =  "(CREATE|create)\\s(PROCEDURE|procedure)\\s\\w*..\\w*";
	//private static final String REGEX_TABLE =  "(CREATE|create)\\s(TABLE|table)\\s\\w*..\\w*";
	private static final String REGEX_TABLE = "(CREATE|create)\\s(TABLE|table)\\s(\\w*)(\\.\\w*)?(\\.\\w*)?"; 
	private static final String REGEX_VIEW =  "(CREATE|create)\\s(VIEW|view)\\s\\w*..\\w*";
	
	public DatabaseElement parseFile(String file) {
		//String objectName = normalizeFilename(file);
		String statements = leer(file);
		DatabaseElement element = identificarObjeto(statements);
		
		return element;
	}
	
	/**
	 * Carga el contenido de un archivo en memoria.
	 * 
	 * @param fileName
	 * @return The sql script in a String object.
	 */
	protected String leer(String fileName) {
		String result = "";
		
		LOGGER.debug("Leyendo: {}", fileName);

		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(fileName);
			result = IOUtils.toString(fis, "UTF-8");
		} catch (IOException e) {
			LOGGER.error("Error al leer el archivo {}: {}", fileName, e.getMessage());
		}
		return result;
	}
	
	/**
	 * Normalizar el nombre del archivo implica quitarle la extensión y
	 * en caso que tuviera, el prefijo del esquema dbo.
	 * ¿Para qué se necesita este proceso de normalización del nombre del archivo?
	 * 
	 * Se pretende inferir a partir del nombre del archivo, del nombre del objeto de base de datos 
	 * que dicho archivo representa.
	 * 
	 * @param filename
	 * @return A normalize String with the filename but the extension and the prefix (E.g.: dbo).
	 */
	@SuppressWarnings("unused")
	private String normalizeFilename(String filename) {
		return removePrefix(removeExtension(filename));
	}
	
	/**
	 * Le quita la extensión al nombre del archivo utilizando la librería Apache Commons IO.
	 * 
	 * @param filename
	 * @return The filename as String without the extension.
	 */
	private String removeExtension(String filename) {
		return FilenameUtils.removeExtension(filename);
	}
	
	/**
	 * Le quita el prefijo 'dbo' que representa el esquema, del nombre del archivo.
	 *  
	 * @param filename
	 * @return The filename as String without the prefix.
	 */
	private String removePrefix(String filename) {
		if (filename.toLowerCase().startsWith("dbo.")) {
			return filename.substring(filename.toLowerCase().indexOf("dbo.") + 4);
		} else {
			return filename;
		}
	}
	
	/**
	 * Analiza el script para identificar de que tipo de objeto de base de datos se trata.
	 *  
	 * @param statements
	 * @return Un objeto DatabaseElement 
	 */
	private DatabaseElement identificarObjeto(String statements) {
		String dbName = identificarNombreBase(REGEX_DATABASE, statements);
		String nombre = "";
		
		if (nombre.equals("")) {
			nombre = identificarNombre(REGEX_SP, statements);
			if (!nombre.equals("")) {
				LOGGER.debug("Se encontró que es un SP: {}", nombre);
				return new DatabaseElement(dbName, Tipo.STORED_PROCEDURE, nombre, statements);
			}
		}
		
		if (nombre.equals("")) {
			nombre = identificarNombre(REGEX_TABLE, statements);
			if (!nombre.equals("")) {
				LOGGER.debug("Se encontró que es una tabla: {}", nombre);
				return new DatabaseElement(dbName, Tipo.TABLE, nombre, statements);
			}
		}
		
		if (nombre.equals("")) {
			nombre = identificarNombre(REGEX_VIEW, statements);
			if (!nombre.equals("")) {
				LOGGER.debug("Se encontró que es una vista: {}", nombre);
				return new DatabaseElement(dbName, Tipo.VIEW, nombre, statements);
			}
		}
		
		return null;
	}
	
	/**
	 * Analiza la cláusula de uso de base de datos. Si no existe esta cláusula se corre el riesgo de ejecutar
	 * el script sobre otra base de datos accidentalmente.
	 * 
	 * @param regexp
	 * @param statements
	 * @return
	 */
	private String identificarNombreBase(String regexp, String statements) {
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(statements);
		
		if (matcher.find()) {
			String[] parts = matcher.group().split(" ");
			if (parts[1].toLowerCase().contains("\ngo")) {
				return parts[1].substring(0, parts[1].length() - 3);
			} else {
				return parts[1];
			}
		}
		
		return "";
	}
	
	/**
	 * Analiza la cláusula de creación de objetos para determinar y devolver el nombre 
	 * del objeto de base de datos a crear.
	 *  
	 * @param regexp
	 * @param statements
	 * @return Un String con el nombre del objeto a crear.
	 */
	private String identificarNombre(String regexp, String statements) {
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(statements);
		
		if (matcher.find()) {
			String nombre = "";
			
			// En la instrucción completa, identifico el nombre del objeto. Ej. CREATE PROCEDURE CREATE_CATALOGO_TMP
			String[] parts = matcher.group().split("\\s");
			String clausula = parts[2];
			
			// Si existe, discrimino el esquema
			if (clausula.contains("..")) {
				String[] names = matcher.group().split("..");
				nombre = names[1];
			} else {
				// Por último, busco el esquema explicitamente...
				if (clausula.contains(".")) {
					String[] names = clausula.split("\\.");
					nombre = names[(names.length == 2) ? 1 : 2];
				}
			}
			return nombre;
		}
		return "";
	}
}
