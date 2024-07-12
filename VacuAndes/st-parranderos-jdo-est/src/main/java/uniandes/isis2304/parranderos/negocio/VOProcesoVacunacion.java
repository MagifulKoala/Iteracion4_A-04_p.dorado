package uniandes.isis2304.parranderos.negocio;

public interface VOProcesoVacunacion {
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the numDosis
	 */
	public int getNumDosis();
	
	/**
	 * @return the dosisAplicadas
	 */
	public int getDosisAplicadas();
	
	/**
	 * @return the vacunado
	 */
	public int getVacunado();
	
	
	/**
	 * @return the concentimiento
	 */
	public int getConcentimiento();
	
	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario(); 
	
	/**
	 * @return Una cadena de caracteres con la información básica de el proceso de vacunacion
	 */
	@Override
	public String toString(); 

}
