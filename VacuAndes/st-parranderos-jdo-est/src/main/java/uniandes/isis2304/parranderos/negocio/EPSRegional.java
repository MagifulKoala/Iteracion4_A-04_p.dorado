package uniandes.isis2304.parranderos.negocio;

public class EPSRegional implements VOEPSRegional
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador de la eps regional
	 */
	private long id;
	
	/**
	 * nombre de la EPSRegional
	 */
	private String nombre;
	
	/**
	 * telefono de la eps regional
	 */
	private int telefono;
	
	/**
	 * Region donde recide la eps regional
	 */
	private String region;
	
	/**
	 * ministerio al cual pertence la EPS Regional
	 */
	private long idMinisterio;
	
	
	/**
	 * capacidad total de dosis de vacunas que puede alamacenar 
	 */
	private int capacidadVacunas; 
	

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * constructor por defecto
	 */
	public EPSRegional()
	{
		this.id = 0;
		this.nombre = "";
		this.telefono = 0;
		this.region = "";
		this.idMinisterio = 0;
		this.capacidadVacunas = 0; 
	}
	
	/**
	 * constructor con parametros
	 */
	public EPSRegional(long id, String nombre, int telefono, String region, long idMinisterio, int capacidadVacunas)
	{
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.region = region;
		this.idMinisterio = idMinisterio;
		this.capacidadVacunas = capacidadVacunas; 
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
	 * @return the telefono
	 */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the idMinisterio
	 */
	public long getIdMinisterio() {
		return idMinisterio;
	}

	/**
	 * @param idMinisterio the idMinisterio to set
	 */
	public void setIdMinisterio(long idMinisterio) {
		this.idMinisterio = idMinisterio;
	}
	
	
	public int getCapacidadVacunas() {
		return capacidadVacunas;
	}

	public void setCapacidadVacunas(int capacidadVacunas) {
		this.capacidadVacunas = capacidadVacunas;
	}

	/**
	 * @return Una cadena de caracteres con la información básica de la EPSRegional
	 */
	@Override
	public String toString() 
	{
		return "EPSRegional [id=" + id + ", nombre=" + nombre + ", telefini=" + telefono + ", region=" + region + ", idMinisterio = "+idMinisterio+"]";
	}
	
}
