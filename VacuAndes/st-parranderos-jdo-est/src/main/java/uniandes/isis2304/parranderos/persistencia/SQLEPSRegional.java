package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.EPSRegional;

class SQLEPSRegional 
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
	public SQLEPSRegional(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	//crear una nueva EPSRegional
	public long agregarEPSRegional(PersistenceManager pm, long id, String nombre, int telefono, String region, long idMinisterio, int capacidadVacunas) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darEPSRegional() + "(ID, NOMBRE, TELEFONO, REGION, IDMINISTERIO, CAPACIDADVACUNAS) values (?, ?,?,?,?,?)");
        q.setParameters(id, nombre, telefono, region, idMinisterio, capacidadVacunas);
        return (long) q.executeUnique();
	}
	
	//dar EPS por id
	public EPSRegional darEPSPorID (PersistenceManager pm, long idEPS) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pv.darEPSRegional() + " WHERE id = ?");
		q.setResultClass(EPSRegional.class);
		q.setParameters(idEPS);
		return (EPSRegional) q.executeUnique();
	}
	
	//calcula la cantidad actual de vacunas registradas en la EPS
	public int cantidadActualvacunasEPS(PersistenceManager pm, long idEPS) 
	{
		String str = "";
		
		str += " SELECT NVL(SUM(cantidad),0) ";
		str += " FROM tienevacuna t, vacuna v";
		str += " WHERE v.id = t.IDVACUNA";
		str += " AND t.IDEPS = ?";
		
		
		Query q = pm.newQuery(SQL, str); 
		q.setParameters(idEPS);
		return ((BigDecimal) q.executeUnique()).intValue(); 
	}
	
	
	//cantidad de vacunas suministradas en un rangod e fechas 
	public List<Object> darVacunasSuminstradasPorEPS (PersistenceManager pm, Timestamp fechaI, Timestamp fechaF)
	{
		String str = ""; 
		
		str += " SELECT t.IDEPS, COUNT(d.ID)";
		str += " FROM dosisVacuna d, cita c, tieneVacuna t";
		str += " WHERE d.IDPUNTOVACUNACION = c.IDPUNTOVACUNACION";
		str += " AND t.IDVACUNA = d.IDVACUNA";
		str += " AND c.FECHAHORA >= ?";
		str += " AND C.FECHAHORA <= ?";
		str += " AND c.ESTADO = 'FINALIZADA'";
		str += " AND d.APLICADA = 1";
		str += " GROUP BY t.IDEPS";
		str += " ORDER BY COUNT(d.ID) DESC";
		str += " FETCH FIRST 3 row ONLY"; 
		
		
		Query q = pm.newQuery(SQL, str);
		q.setParameters(fechaI, fechaF);
		return q.executeList();
	}

}
