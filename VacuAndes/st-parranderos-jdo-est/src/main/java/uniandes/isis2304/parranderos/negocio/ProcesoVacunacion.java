package uniandes.isis2304.parranderos.negocio;

public class ProcesoVacunacion implements VOProcesoVacunacion
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador del proceso de vacunacion
	 */
	private long id;
	
	/**
	 * numero de dosis asignadas
	 */
	private int numDosis;
	
	/**
	 * numero de dosis aplicadas
	 */
	private int dosisAplicadas;
	
	/**
	 * estado si esta vacunado o no
	 */
	private int vacunado;
	
	/**
	 * estado si dio concentimiento o no
	 */
	private int concentimiento;
	
	/**
	 * usuario asociado al proceso de vacunacion
	 */
	private long idUsuario; 
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * constructor por defecto
	 */
	public ProcesoVacunacion()
	{
		this.id = 0;
		this.numDosis = 0;
		this.dosisAplicadas = 0; 
		this.vacunado = 0;
		this.concentimiento = 0;
		this.idUsuario = 0; 
	}
	
	/**
	 * constructor con parametros
	 */
	public ProcesoVacunacion(long id, int numDosis, int dosisAplicadas, int vacunado, int concentimiento, long idUsuario)
	{
		this.id = id;
		this.numDosis = numDosis;
		this.dosisAplicadas = dosisAplicadas; 
		this.vacunado = vacunado;
		this.concentimiento = concentimiento;
		this.idUsuario = idUsuario;
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
	 * @return the dosisAplicadas
	 */
	public int getDosisAplicadas() {
		return dosisAplicadas;
	}

	/**
	 * @param dosisAplicadas the dosisAplicadas to set
	 */
	public void setDosisAplicadas(int dosisAplicadas) {
		this.dosisAplicadas = dosisAplicadas;
	}

	/**
	 * @return the vacunado
	 */
	public int getVacunado() {
		return vacunado;
	}

	/**
	 * @param vacunado the vacunado to set
	 */
	public void setVacunado(int vacunado) {
		this.vacunado = vacunado;
	}

	/**
	 * @return the concentimiento
	 */
	public int getConcentimiento() {
		return concentimiento;
	}

	/**
	 * @param concentimiento the concentimiento to set
	 */
	public void setConcentimiento(int concentimiento) {
		this.concentimiento = concentimiento;
	}
	
	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return Una cadena de caracteres con la información básica de el proceso de vacunacion
	 */
	@Override
	public String toString() 
	{
		return "ProcesoVacunacion [id=" + id + ", numDosis=" + numDosis + ", dosisAplicadas=" + dosisAplicadas + ", vacunado=" + vacunado +", concentimiento = "+ concentimiento +"]";
	}
	
	

}
