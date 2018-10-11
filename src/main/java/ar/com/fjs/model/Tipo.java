package ar.com.fjs.model;

public enum Tipo {
	TABLE("Table"), 
	STORED_PROCEDURE("Stored procedure"), 
	VIEW("View"), 
	PRIMARY_KEY("Primary key"), 
	CONSTRAINT("Constraint"), 
	FOREIGN_KEY("Foreing key");
	
	private String descripcion;
	
	private Tipo(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return this.descripcion;
	}
}
