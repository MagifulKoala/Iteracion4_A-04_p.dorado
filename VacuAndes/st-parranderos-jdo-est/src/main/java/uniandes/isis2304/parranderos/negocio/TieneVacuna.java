package uniandes.isis2304.parranderos.negocio;

public class TieneVacuna implements VOTieneVacuna 
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador de la vacuna
	 */
	private long idVacuna;
	
	/**
	 * identificador de la EPS regional
	 */
	private long idEPS;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * constructor por defecto
	 */
	public TieneVacuna()
	{
		this.idVacuna = 0;
		this.idEPS = 0; 
	}
	
	/**
	 * constructor con parametros
	 */
	public TieneVacuna(long idVacuna, long idEPS)
	{
		this.idVacuna = idVacuna;
		this.idEPS = idEPS; 
	}

	/**
	 * @return the idVacuna
	 */
	public long getIdVacuna() {
		return idVacuna;
	}

	/**
	 * @param idVacuna the idVacuna to set
	 */
	public void setIdVacuna(long idVacuna) {
		this.idVacuna = idVacuna;
	}

	/**
	 * @return the idEPS
	 */
	public long getIdEPS() {
		return idEPS;
	}

	/**
	 * @param idEPS the idEPS to set
	 */
	public void setIdEPS(long idEPS) {
		this.idEPS = idEPS;
	}
	
	/**
	 * @return Una cadena de caracteres con la información básica de Tienevacuna
	 */
	@Override
	public String toString() 
	{
		return "TieneVacuna [idVacuna=" + idVacuna + ", idEPS=" + idEPS +"]";
	}
	
}
