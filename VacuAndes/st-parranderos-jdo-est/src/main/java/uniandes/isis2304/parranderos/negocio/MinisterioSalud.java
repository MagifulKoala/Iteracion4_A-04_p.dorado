/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto BAR del negocio de los Parranderos
 *
 * @author Germán Bravo
 */
public class MinisterioSalud implements VOMinisterioSalud
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * el identificador del ministerio de salud
	 */
	private long id;
	
	/**
	 * el contenido del plan de vacunacion
	 */
	private String priorizacionVacuna; 
	

	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public MinisterioSalud()
	{
		this.id = 0; 
		this.priorizacionVacuna = "";
	}
	
	/**
	 * constructor con valores
	 * @param id - identificador unico del ministerio
	 * @param priorizacionVacuna -descripcion del plan de vacunacion 
	 */
	public MinisterioSalud(long id, String priorizacionVacuna )
	{
		this.id = id; 
		this.priorizacionVacuna = priorizacionVacuna;
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
	 * @return the priorizacionVacuna
	 */
	public String getPriorizacionVacuna() {
		return priorizacionVacuna;
	}

	/**
	 * @param priorizacionVacuna the priorizacionVacuna to set
	 */
	public void setPriorizacionVacuna(String priorizacionVacuna) {
		this.priorizacionVacuna = priorizacionVacuna;
	}

	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del Ministerio de Salud
	 */
	public String toString() 
	{
		return "Ministerio de Salud [id=" + id + ", priorizacion vacuna = " + priorizacionVacuna + "]";
	}
	

}
