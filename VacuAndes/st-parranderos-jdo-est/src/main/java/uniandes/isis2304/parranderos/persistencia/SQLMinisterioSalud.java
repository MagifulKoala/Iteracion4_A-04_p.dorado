package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.MinisterioSalud;

class SQLMinisterioSalud 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaVacuAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaVacuAndes pv;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLMinisterioSalud(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	//crear un ministerio de salud
	public long agregarMinisterioSalud(PersistenceManager pm, long id, String priorizacionVacuna) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darMinisterioSalud() + "(ID, PRIORIZACIONVACUNA) values (?, ?)");
        q.setParameters(id, priorizacionVacuna);
        return (long) q.executeUnique();
	}
	
	
	//da el ministerio de salud
	
	public List<MinisterioSalud> darMinisterioSalud(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM MINISTERIOSALUD m");
		q.setResultClass(MinisterioSalud.class);
		return (List<MinisterioSalud>) q.executeList(); 
	}
	

}
