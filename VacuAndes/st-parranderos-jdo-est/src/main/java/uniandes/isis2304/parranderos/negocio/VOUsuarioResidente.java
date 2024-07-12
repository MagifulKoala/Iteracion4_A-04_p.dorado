package uniandes.isis2304.parranderos.negocio;

public interface VOUsuarioResidente
{
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the nombre
	 */
	public String getNombre(); 
	
	/**
	 * @return the edad
	 */
	public int getEdad();
	
	/**
	 * @return the telefono
	 */
	public int getTelefono(); 
	
	/**
	 * @return the condicionesMedicas
	 */
	public String getCondicionesMedicas(); 
	
	/**
	 * @return the profesion
	 */
	public String getProfesion(); 
	
	/**
	 * @return the fase
	 */
	public int getFase();
	
	/**
	 * @return the etapa
	 */
	public int getEtapa(); 
	
	/**
	 * @return the prioridad
	 */
	public int getPrioridad(); 
	
	/**
	 * @return the idEPS
	 */
	public long getIdEPS(); 
	
	/**
	 * @return the idPuntoVacunacion
	 */
	public long getIdPuntoVacunacion();
	
	/**
	 * @return the idTrabaja
	 */
	public long getIdTrabaja(); 

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(); 

}
