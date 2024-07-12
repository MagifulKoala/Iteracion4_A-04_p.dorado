package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Cita implements VOCita
{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador de la cita
	 */
	private long id;
	
	/**
	 * fecha y hora de la cita
	 */
	private Timestamp fechaHora;
	
	/**
	 * estado de la cita
	 */
	private String estado;
	
	/**
	 * identificador de la EPS asociada
	 */
	private long idEPS;
	
	/**
	 * identificador del punto de vacunacion 
	 */
	private long idPuntoVacunacion;
	
	/**
	 * identificador del usuario que tiene la cita
	 */
	private long idUsuario;



	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * constructor por defecto
	 */
	public Cita() {
		this.id = 0;
		this.fechaHora = null;
		this.estado = "";
		this.idEPS = 0;
		this.idPuntoVacunacion = 0;
		this.idUsuario = 0;
	}
	
	/**
	 * constructor con parametros
	 * @param id de la cita
	 * @param fechaHora de la cita
	 * @param estado de la cita
	 * @param idEPS de la cita
	 * @param idPuntoVacunacion de la cita
	 * @param idUsuario de la cita
	 */
	public Cita(long id, Timestamp fechaHora, String estado, long idEPS, long idPuntoVacunacion, long idUsuario) {
		this.id = id;
		this.fechaHora = fechaHora;
		this.estado = estado;
		this.idEPS = idEPS;
		this.idPuntoVacunacion = idPuntoVacunacion;
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the fechaHora
	 */
	public Timestamp getFechaHora() {
		return fechaHora;
	}

	/**
	 * @param fechaHora the fechaHora to set
	 */
	public void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
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
	 * @return the idPuntoVacunacion
	 */
	public long getIdPuntoVacunacion() {
		return idPuntoVacunacion;
	}

	/**
	 * @param idPuntoVacunacion the idPuntoVacunacion to set
	 */
	public void setIdPuntoVacunacion(long idPuntoVacunacion) {
		this.idPuntoVacunacion = idPuntoVacunacion;
	}

	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cita [id=" + id + ", fechaHora=" + fechaHora + ", estado=" + estado + ", idEPS=" + idEPS
				+ ", idPuntoVacunacion=" + idPuntoVacunacion + ", idUsuario=" + idUsuario + "]";
	}
	
	

	

}
