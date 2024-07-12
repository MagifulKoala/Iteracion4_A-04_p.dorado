package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class EntradaPlan implements VOEntradaPlan {
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador unico de la entrada del plan
	 */
	private long id;
	
	/**
	 * fase del plan 
	 */
	private int fase;
	
	/**
	 * etapa del plan
	 */
	private int etapa;
	
	/**
	 * fecha de inicio de la fase y etapa determinados
	 */
	private Timestamp fechaInicio; 
	
	/**
	 * fecha de finalizacion de la fase y etapa determinados
	 */
	private Timestamp fechaFin;
	
	/**
	 * condiciones aplicables a la dase y etapa determinados
	 */
	private String condiciones;
	
	/**
	 * ministerio al cual se le asocia el plan de vacunacion 
	 */
	private long idMinisterio;
	
	
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * constructor por defecto
	 */
	public EntradaPlan()
	{
		this.id = 0; 
		this.fase = 0;
		this.etapa = 0;
		this.fechaInicio = null;
		this.fechaFin = null;
		this.condiciones = "";
		this.idMinisterio = 0;
	}
	
	/**
	 * constructor con parametros
	 * @param id de la entrada del plan
	 * @param fase de la entrada del plan
	 * @param etapa de la entrada del plan
	 * @param fechaInicio de la entrada del plan
	 * @param fechaFin de la entrada del plan
	 * @param condiciones de la entrada del plan
	 * @param idMinisterio de la entrada del plan
	 */
	public EntradaPlan(long id, int fase, int etapa, Timestamp fechaInicio, Timestamp fechaFin, String condiciones, long idMinisterio)
	{
		this.id = id; 
		this.fase = fase;
		this.etapa = etapa;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.condiciones = condiciones;
		this.idMinisterio = idMinisterio;
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
	 * @return the fase
	 */
	public int getFase() {
		return fase;
	}

	/**
	 * @param fase the fase to set
	 */
	public void setFase(int fase) {
		this.fase = fase;
	}

	/**
	 * @return the etapa
	 */
	public int getEtapa() {
		return etapa;
	}

	/**
	 * @param etapa the etapa to set
	 */
	public void setEtapa(int etapa) {
		this.etapa = etapa;
	}

	/**
	 * @return the fechaInicio
	 */
	public Timestamp getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Timestamp fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public Timestamp getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Timestamp fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the condiciones
	 */
	public String getCondiciones() {
		return condiciones;
	}

	/**
	 * @param condiciones the condiciones to set
	 */
	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}

	/**
	 * @return the idMinisterio
	 */
	public long getIdMinisterio() {
		return idMinisterio;
	}

	/**
	 * @param idMinisterio the idMinisterio to set
	 */
	public void setIdMinisterio(long idMinisterio) {
		this.idMinisterio = idMinisterio;
	}
	
	/**
	 * @return Una cadena de caracteres con la información básica de la entrada del plan de vacunacion 
	 */
	@Override
	public String toString() 
	{
		return "Entrada Plan [id=" + id + ", fase=" + fase + ", etapa=" + etapa + ", fechaInicio=" + fechaInicio + " , fechaFin = "+fechaFin+", condiciones = "+condiciones+", idMinisterio = "+idMinisterio+"]";
	}
	
	
	

}
