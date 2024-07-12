package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLEntradaPlan 
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
	public SQLEntradaPlan(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	
	public long crearEntradaPlan(PersistenceManager pm, long id, int fase, int etapa, Timestamp fechaInicio, Timestamp fechaFin, String condiciones, long idMinisterio) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darEntradaPlan() + "(ID, FASE,ETAPA, FECHAINICIO,FECHAFIN,CONDICIONES,IDMINISTERIO) VALUES(?,?,?,?,?,?,?)");
        q.setParameters(id, fase, etapa, fechaInicio, fechaFin, condiciones, idMinisterio);
        return (long) q.executeUnique();
	}

}
