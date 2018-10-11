package ar.com.fjs.model;

public class DatabaseElement {
	private Tipo tipo;
	private String nombreBase;
	private String nombre;
	private String script;
	
	public DatabaseElement(String nombreBase, Tipo tipo, String nombre, String script) {
		this.nombreBase = nombreBase;
		this.tipo = tipo;
		this.nombre = nombre;
		this.script = script;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}

	public String getNombreBase() {
		return nombreBase;
	}

	public void setNombreBase(String nombreBase) {
		this.nombreBase = nombreBase;
	}

	@Override
	public String toString() {
		return "DatabaseElement [tipo=" + tipo + ", nombreBase=" + nombreBase + ", nombreObjeto=" + nombre + "]";
	}
}
