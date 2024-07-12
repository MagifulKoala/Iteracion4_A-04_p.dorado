package uniandes.isis2304.parranderos.negocio;

public interface VOTieneVacuna 
{
	/**
	 * @return the idVacuna
	 */
	public long getIdVacuna();
	
	/**
	 * @return the idEPS
	 */
	public long getIdEPS();
	
	/**
	 * @return Una cadena de caracteres con la información básica de Tienevacuna
	 */
	@Override
	public String toString(); 

}
