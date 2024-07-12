package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.EPSRegional;
import uniandes.isis2304.parranderos.negocio.Vacuna;

class SQLVacuna 
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
	public SQLVacuna(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	public long agregarVacuna(PersistenceManager pm, long id, String nombre, String informacion, int numDosis, String condiciones, int cantidad)
	{
		Query q = pm.newQuery(SQL,"INSERT INTO "+ pv.darVacuna() +" (ID, NOMBRE, INFORMACION, NUMDOSIS, CONDICIONES,CANTIDAD) VALUES(?, ?, ?, ?, ?, ?)");
		q.setParameters(id, nombre, informacion, numDosis, condiciones, cantidad);
		return (long) q.executeUnique();
	}
	
	//da la vacuna dado un id
	public Vacuna darVacunaPorID (PersistenceManager pm, long idVacuna) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pv.darVacuna() + " WHERE id = ?");
		q.setResultClass(Vacuna.class);
		q.setParameters(idVacuna);
		return (Vacuna) q.executeUnique();
	}
	

}
