package ar.com.fjs.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

import ar.com.fjs.model.DatabaseElement;
import ar.com.fjs.model.Tipo;

public class FileServiceTest {
	private static FileService service;
	
	@BeforeClass
	public static void initialize() {
		service = new FileService();
	}
	
	@Test
	public void testCrearDataElementDesdeArchivo() {
		String filename = "dbo.AREA.sql";
		DatabaseElement tabla = service.parseFile("/home/fernando/development/eclipse-workspace/SqlValidator/db/estructura/" + filename);
		
		assertNotNull("El objeto es nulo", tabla);
	}
	
	@Test
	public void testValidarDataElementCreadoDesdeArchivo() {
		String filename = "dbo.AREA.sql";
		DatabaseElement tabla = service.parseFile("/home/fernando/development/eclipse-workspace/SqlValidator/db/estructura/" + filename);
		
		assertThat("El nombre de la tabla es incorrecto", tabla.getNombre(), is(equalTo("AREA")));
		assertThat("El tipo del objecto es incorrecto", tabla.getTipo(), is(equalTo(Tipo.TABLE)));
	}
	
	@Test
	public void testLeerArchivoDevuelveString() {
		String filename = "dbo.AREA.sql";
		String resultado = service.leer("/home/fernando/development/eclipse-workspace/SqlValidator/db/estructura/" + filename);
		assertNotNull("No se pudo obtener el String del script", resultado);
	}
}
