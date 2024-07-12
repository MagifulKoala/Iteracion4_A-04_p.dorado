package uniandes.isis2304.parranderos.negocio;

public class PuntoVacunacion implements VOPuntoVacunacion
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador del punto de vacunacion
	 */
	private long id;
	
	/**
	 * localizacion del punto de vacunacion
	 */
	private String localizacion;
	
	/**
	 * capacidad simultanea del punto de vacunacion
	 */
	private int capacidadSimultanea;
	
	/**
	 * capacidad total diaria
	 */
	private int capacidadTotalDiaria;
	
	/**
	 * descripcion de la infraestructura del punto de vacunacion
	 */
	private String infraestructura;
	
	/**
	 * el tipo de punto de vacunacion
	 */
	private String tipo;
	
	/**
	 * identificador de la EPS asociada
	 */
	private long idEPS;
	
	/**
	 * estado del punto de vacunacion
	 */
	private String estado; 
	
	/**
	 * la capacidad total maxima de personas registradas al mismo tiempo 
	 */
	private long capacidadTotal; 
	
	/**
	 * capacodad maxima de dosis que el punto puede tener al mismo tiempo
	 */
	
	private long capacidadDosis; 
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * constructor por defecto
	 */
	public PuntoVacunacion()
	{
		this.id = 0;
		this.localizacion = "";
		this.capacidadSimultanea = 0;
		this.capacidadTotalDiaria = 0;
		this.infraestructura = "";
		this.tipo = "";
		this.idEPS = 0; 
		this.estado = "";
		this.capacidadTotal = 0;
		this.capacidadDosis = 0; 
	}
	
	
	/**
	 * constructor con parametros
	 */
	public PuntoVacunacion(long id, String localizacion, int capacidadSimultanea, int capacidadTotalDiaria, String infraestructura, String tipo, long idEPS, String estado, long capacidadTotal, long capacidadDosis)
	{
		this.id = id;
		this.localizacion = localizacion;
		this.capacidadSimultanea = capacidadSimultanea;
		this.capacidadTotalDiaria = capacidadTotalDiaria;
		this.infraestructura = infraestructura;
		this.tipo = tipo;
		this.idEPS = idEPS; 
		this.estado = estado;
		this.capacidadTotal = capacidadTotal; 
		this.capacidadDosis = capacidadDosis;
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
	 * @return the localizacion
	 */
	public String getLocalizacion() {
		return localizacion;
	}


	/**
	 * @param localizacion the localizacion to set
	 */
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}


	/**
	 * @return the capacidadSimultanea
	 */
	public int getCapacidadSimultanea() {
		return capacidadSimultanea;
	}


	/**
	 * @param capacidadSimultanea the capacidadSimultanea to set
	 */
	public void setCapacidadSimultanea(int capacidadSimultanea) {
		this.capacidadSimultanea = capacidadSimultanea;
	}


	/**
	 * @return the capacidadTotalDiaria
	 */
	public int getCapacidadTotalDiaria() {
		return capacidadTotalDiaria;
	}


	/**
	 * @param capacidadTotalDiaria the capacidadTotalDiaria to set
	 */
	public void setCapacidadTotalDiaria(int capacidadTotalDiaria) {
		this.capacidadTotalDiaria = capacidadTotalDiaria;
	}


	/**
	 * @return the infraestructura
	 */
	public String getInfraestructura() {
		return infraestructura;
	}


	/**
	 * @param infraestructura the infraestructura to set
	 */
	public void setInfraestructura(String infraestructura) {
		this.infraestructura = infraestructura;
	}


	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	/**
	 * @return the idEPS
	 */
	public long getIdEPS() {
		return idEPS;
	}


	/**
	 * @param idEPS the idEPS to set
	 */
	public void setIdEPS(long idEPS) {
		this.idEPS = idEPS;
	}
	
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}


	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}


	public long getCapacidadTotal() {
		return capacidadTotal;
	}


	public void setCapacidadTotal(long capacidadTotal) {
		this.capacidadTotal = capacidadTotal;
	}


	public long getCapacidadDosis() {
		return capacidadDosis;
	}


	public void setCapacidadDosis(long capacidadDosis) {
		this.capacidadDosis = capacidadDosis;
	}


	@Override
	public String toString() {
		return "PuntoVacunacion [id=" + id + ", localizacion=" + localizacion + ", capacidadSimultanea="
				+ capacidadSimultanea + ", capacidadTotalDiaria=" + capacidadTotalDiaria + ", infraestructura="
				+ infraestructura + ", tipo=" + tipo + ", idEPS=" + idEPS + ", estado=" + estado + ", capacidadTotal="
				+ capacidadTotal + ", capacidadDosis=" + capacidadDosis + "]";
	}





}
