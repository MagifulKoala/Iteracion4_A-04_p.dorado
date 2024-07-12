package uniandes.isis2304.parranderos.negocio;

public class Vacuna implements VOVacuna
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador de la vacuna
	 */
	private long id;
	
	/**
	 * nombre de la vacuna
	 */
	private String nombre;
	
	/**
	 * informacion de la vacuna
	 */
	private String informacion;
	
	/**
	 * numero de dosis requeridas para la vacuna
	 */
	private int numDosis;
	
	/**
	 * condiciones de almacenamiento de la vacuna
	 */
	private String condiciones;
	
	/**
	 * numero total de dosis disponibles de la vacuna
	 */
	private int cantidad;
	
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * constructor pot defecto
	 */
	public Vacuna()
	{
		this.id = 0;
		this.nombre = "";
		this.informacion = "";
		this.numDosis = 0;
		this.condiciones = "";
		this.cantidad = 0;
	}
	
	/**
	 * constructor con parametros
	 */
	public Vacuna(long id, String nombre, String informacion, int numDosis, String condiciones, int cantidad)
	{
		this.id = id;
		this.nombre = nombre;
		this.informacion = informacion;
		this.numDosis = numDosis;
		this.condiciones = condiciones;
		this.cantidad = cantidad;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the informacion
	 */
	public String getInformacion() {
		return informacion;
	}

	/**
	 * @param informacion the informacion to set
	 */
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	/**
	 * @return the numDosis
	 */
	public int getNumDosis() {
		return numDosis;
	}

	/**
	 * @param numDosis the numDosis to set
	 */
	public void setNumDosis(int numDosis) {
		this.numDosis = numDosis;
	}

	/**
	 * @return the condiciones
	 */
	public String getCondiciones() {
		return condiciones;
	}

	/**
	 * @param condiciones the condiciones to set
	 */
	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	/**
	 * @return Una cadena de caracteres con la información básica de la Vacuna
	 */
	@Override
	public String toString() 
	{
		return "Vacuna [id=" + id + ", nombre=" + nombre + ", informacion=" + informacion + ", numDosis=" + numDosis + ", condiciones = "+condiciones+", cantidad = "+ cantidad+"]";
	}

}
