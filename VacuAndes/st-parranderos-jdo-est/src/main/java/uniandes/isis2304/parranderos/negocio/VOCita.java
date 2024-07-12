package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOCita 
{
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the fechaHora
	 */
	public Timestamp getFechaHora(); 
	
	/**
	 * @return the estado
	 */
	public String getEstado(); 
	
	/**
	 * @return the idEPS
	 */
	public long getIdEPS();
	
	/**
	 * @return the idPuntoVacunacion
	 */
	public long getIdPuntoVacunacion(); 
	
	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(); 

}
