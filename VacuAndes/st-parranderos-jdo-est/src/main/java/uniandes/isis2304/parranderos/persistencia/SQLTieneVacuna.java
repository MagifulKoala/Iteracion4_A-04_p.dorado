package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

class SQLTieneVacuna 
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
	public SQLTieneVacuna(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	//asignar vacuna a eps
	public long agregarTieneVacuna(PersistenceManager pm, long idVacuna, long idEPS) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darTieneVacuna() + "(IDVACUNA, IDEPS) VALUES (?, ?)");
        q.setParameters(idVacuna, idEPS);
        return (long) q.executeUnique();
	}

}
