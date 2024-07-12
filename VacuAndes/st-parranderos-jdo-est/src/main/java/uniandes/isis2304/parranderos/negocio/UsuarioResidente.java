package uniandes.isis2304.parranderos.negocio;

public class UsuarioResidente implements VOUsuarioResidente
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador del usuario
	 */
	private long id;
	
	/**
	 * nombre del usuario
	 */
	private String nombre;
	
	/**
	 * edad del usuario
	 */
	private int edad;
	
	/**
	 * telefono del usuario
	 */
	private int telefono;
	
	/**
	 * condicionesMedicas del usuario
	 */
	private String condicionesMedicas;
	
	/**
	 * profesion del usuario
	 */
	private String profesion; 
	
	/**
	 * fase en la que se encuentra el usuario
	 */
	private int fase;
	
	/**
	 * etapa en la cual se encuentra el usuario
	 */
	private int etapa;
	
	/**
	 * prioridad que tiene el usuario
	 */
	private int prioridad;
	
	/**
	 * identificador de la EPS del usuario
	 */
	private long idEPS;
	
	/**
	 * identificador del punto de vacunacion
	 */
	private long idPuntoVacunacion;
	
	/**
	 * identificador del punto de vacunacion donde trabaja (si lo hace)
	 */
	private long idTrabaja; 

	
	
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * constructor por defecto
	 */
	public UsuarioResidente() {
		this.id = 0;
		this.nombre = "";
		this.edad = 0;
		this.telefono = 0;
		this.condicionesMedicas = "";
		this.profesion = "";
		this.fase = 0;
		this.etapa = 0;
		this.prioridad = 0;
		this.idEPS = 0;
		this.idPuntoVacunacion = 0;
		this.idTrabaja = 0;
	}
	
	/**
	 * constructor con parametros
	 * @param id del usuario
	 * @param nomre del usuario
	 * @param edad del usuario
	 * @param telefono del usuario
	 * @param condicionesMedicas del usuario
	 * @param profesion del usuario
	 * @param fase del usuario
	 * @param etapa del usuario
	 * @param prioridad del usuario
	 * @param idEPS del usuario
	 * @param idPuntoVacunacion del usuario
	 * @param idTrabaja del usuario
	 * @param idProcesoVacunacion del usuario
	 */
	public UsuarioResidente(long id, String nombre, int edad, int telefono, String condicionesMedicas, String profesion,
			int fase, int etapa, int prioridad, long idEPS, long idPuntoVacunacion, long idTrabaja) {
		this.id = id;
		this.nombre = nombre;
		this.edad = edad;
		this.telefono = telefono;
		this.condicionesMedicas = condicionesMedicas;
		this.profesion = profesion;
		this.fase = fase;
		this.etapa = etapa;
		this.prioridad = prioridad;
		this.idEPS = idEPS;
		this.idPuntoVacunacion = idPuntoVacunacion;
		this.idTrabaja = idTrabaja;
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
	 * @return the edad
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * @param edad the edad to set
	 */
	public void setEdad(int edad) {
		this.edad = edad;
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
	 * @return the condicionesMedicas
	 */
	public String getCondicionesMedicas() {
		return condicionesMedicas;
	}

	/**
	 * @param condicionesMedicas the condicionesMedicas to set
	 */
	public void setCondicionesMedicas(String condicionesMedicas) {
		this.condicionesMedicas = condicionesMedicas;
	}

	/**
	 * @return the profesion
	 */
	public String getProfesion() {
		return profesion;
	}

	/**
	 * @param profesion the profesion to set
	 */
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	/**
	 * @return the fase
	 */
	public int getFase() {
		return fase;
	}

	/**
	 * @param fase the fase to set
	 */
	public void setFase(int fase) {
		this.fase = fase;
	}

	/**
	 * @return the etapa
	 */
	public int getEtapa() {
		return etapa;
	}

	/**
	 * @param etapa the etapa to set
	 */
	public void setEtapa(int etapa) {
		this.etapa = etapa;
	}

	/**
	 * @return the prioridad
	 */
	public int getPrioridad() {
		return prioridad;
	}

	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
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
	 * @return the idPuntoVacunacion
	 */
	public long getIdPuntoVacunacion() {
		return idPuntoVacunacion;
	}

	/**
	 * @param idPuntoVacunacion the idPuntoVacunacion to set
	 */
	public void setIdPuntoVacunacion(long idPuntoVacunacion) {
		this.idPuntoVacunacion = idPuntoVacunacion;
	}

	/**
	 * @return the idTrabaja
	 */
	public long getIdTrabaja() {
		return idTrabaja;
	}

	/**
	 * @param idTrabaja the idTrabaja to set
	 */
	public void setIdTrabaja(long idTrabaja) {
		this.idTrabaja = idTrabaja;
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UsuarioResidente [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", telefono=" + telefono
				+ ", condicionesMedicas=" + condicionesMedicas + ", profesion=" + profesion + ", fase=" + fase
				+ ", etapa=" + etapa + ", prioridad=" + prioridad + ", idEPS=" + idEPS + ", idPuntoVacunacion="
				+ idPuntoVacunacion + ", idTrabaja=" + idTrabaja +"]";
	}
	

}
