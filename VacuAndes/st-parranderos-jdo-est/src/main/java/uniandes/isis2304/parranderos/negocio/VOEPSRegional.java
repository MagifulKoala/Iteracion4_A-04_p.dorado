package uniandes.isis2304.parranderos.negocio;

public interface VOEPSRegional {
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the nombre
	 */
	public String getNombre();
	
	/**
	 * @return the telefono
	 */
	public int getTelefono();
	
	/**
	 * @return the region
	 */
	public String getRegion();
	
	/**
	 * @return the idMinisterio
	 */
	public long getIdMinisterio();
	
	public int getCapacidadVacunas(); 
	
	/**
	 * @return Una cadena de caracteres con la información básica de la EPSRegional
	 */
	@Override
	public String toString();

}
