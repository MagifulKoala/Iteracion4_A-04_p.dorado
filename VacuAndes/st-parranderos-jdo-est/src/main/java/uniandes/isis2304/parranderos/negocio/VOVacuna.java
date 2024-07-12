package uniandes.isis2304.parranderos.negocio;

public interface VOVacuna {
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the nombre
	 */
	public String getNombre();
	
	/**
	 * @return the informacion
	 */
	public String getInformacion();
	
	/**
	 * @return the numDosis
	 */
	public int getNumDosis();
	
	/**
	 * @return the condiciones
	 */
	public String getCondiciones(); 
	
	/**
	 * @return the cantidad
	 */
	public int getCantidad();
	
	/**
	 * @return Una cadena de caracteres con la información básica de la Vacuna
	 */
	@Override
	public String toString() ; 

}
