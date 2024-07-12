package uniandes.isis2304.parranderos.negocio;

public interface VORol 
{
	
	/**
	 * @return the id
	 */
	public long getId();
	
	/**
	 * @return the rol
	 */
	public String getRol();
	
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
