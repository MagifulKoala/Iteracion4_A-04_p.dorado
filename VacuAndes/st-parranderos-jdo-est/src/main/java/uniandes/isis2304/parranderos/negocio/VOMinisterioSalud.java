package uniandes.isis2304.parranderos.negocio;

public interface VOMinisterioSalud {
	
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the priorizacionVacuna
	 */
	public String getPriorizacionVacuna();
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString();

}
