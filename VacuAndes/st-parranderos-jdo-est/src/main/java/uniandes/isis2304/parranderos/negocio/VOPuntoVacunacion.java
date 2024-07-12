package uniandes.isis2304.parranderos.negocio;

public interface VOPuntoVacunacion
{
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the localizacion
	 */
	public String getLocalizacion();
	
	/**
	 * @return the capacidadSimultanea
	 */
	public int getCapacidadSimultanea();
	
	/**
	 * @return the capacidadTotalDiaria
	 */
	public int getCapacidadTotalDiaria(); 
	
	/**
	 * @return the infraestructura
	 */
	public String getInfraestructura(); 
	
	/**
	 * @return the tipo
	 */
	public String getTipo(); 
	
	/**
	 * @return the idEPS
	 */
	public long getIdEPS(); 
	
	
	/**
	 * @return the estado
	 */
	public String getEstado(); 
	
	
	public long getCapacidadTotal();
	
	public long getCapacidadDosis(); 
	
	/**
	 * @return Una cadena de caracteres con la información básica del punto de vacunacion
	 */
	@Override
	public String toString(); 

}
