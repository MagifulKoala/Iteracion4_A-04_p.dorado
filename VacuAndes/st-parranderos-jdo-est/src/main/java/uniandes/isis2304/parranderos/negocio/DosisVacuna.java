package uniandes.isis2304.parranderos.negocio;

public class DosisVacuna implements VODosisVacuna
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador de la vacuna
	 */
	private long id;
	
	/**
	 * estado de la dosis
	 */
	private int aplicada; 
	
	/**
	 * identificador del usuario asociado
	 */
	private long idUsuario;
	
	/**
	 * identificador del punto de vacunacion
	 */
	private long idPuntoVacunacion; 
	
	/**
	 * identificador de la vacuna asociada
	 */
	private long idVacuna;


	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * constructor por defecto
	 */
	public DosisVacuna() {
		this.id = 0;
		this.aplicada = 0;
		this.idUsuario = 0;
		this.idPuntoVacunacion = 0;
		this.idVacuna = 0;
	}
	
	
	
	/**
	 * constructor con parametros
	 * @param id de la dosis
	 * @param aplicada de la dosis
	 * @param idUsuario de la dosis
	 * @param idPuntoVacunacion de la dosis
	 * @param idVacuna de la dosis
	 */
	public DosisVacuna(long id, int aplicada, long idUsuario, long idPuntoVacunacion, long idVacuna) {
		this.id = id;
		this.aplicada = aplicada;
		this.idUsuario = idUsuario;
		this.idPuntoVacunacion = idPuntoVacunacion;
		this.idVacuna = idVacuna;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public int getAplicada() {
		return aplicada;
	}



	public void setAplicada(int aplicada) {
		this.aplicada = aplicada;
	}



	public long getIdUsuario() {
		return idUsuario;
	}



	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}



	public long getIdPuntoVacunacion() {
		return idPuntoVacunacion;
	}



	public void setIdPuntoVacunacion(long idPuntoVacunacion) {
		this.idPuntoVacunacion = idPuntoVacunacion;
	}



	public long getIdVacuna() {
		return idVacuna;
	}



	public void setIdVacuna(long idVacuna) {
		this.idVacuna = idVacuna;
	} 
	
	
	

}
