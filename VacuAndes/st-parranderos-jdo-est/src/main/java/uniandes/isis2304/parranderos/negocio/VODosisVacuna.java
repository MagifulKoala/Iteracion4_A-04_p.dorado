package uniandes.isis2304.parranderos.negocio;

public interface VODosisVacuna 
{
	
	public long getId(); 

	public int getAplicada(); 
	
	public long getIdUsuario(); 
	
	public long getIdPuntoVacunacion(); 
	
	public long getIdVacuna();
	
	/**
	 * @return Una cadena de caracteres con la información básica de el proceso de vacunacion
	 */
	@Override
	public String toString(); 

}
