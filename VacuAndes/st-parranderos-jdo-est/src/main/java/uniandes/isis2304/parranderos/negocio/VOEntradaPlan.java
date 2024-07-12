package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOEntradaPlan {
	
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the fase
	 */
	public int getFase();
	
	/**
	 * @return the etapa
	 */
	public int getEtapa();
	
	/**
	 * @return the fechaInicio
	 */
	public Timestamp getFechaInicio();
	
	/**
	 * @return the fechaFin
	 */
	public Timestamp getFechaFin();
	
	/**
	 * @return the condiciones
	 */
	public String getCondiciones();
	
	/**
	 * @return the idMinisterio
	 */
	public long getIdMinisterio();
	
	
	/**
	 * @return Una cadena de caracteres con la información de la entrada del plan 
	 */
	@Override
	public String toString();

}
