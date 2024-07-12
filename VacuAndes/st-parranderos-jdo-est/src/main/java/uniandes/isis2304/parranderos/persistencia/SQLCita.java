package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cita;
import uniandes.isis2304.parranderos.negocio.ProcesoVacunacion;
import uniandes.isis2304.parranderos.negocio.UsuarioResidente;

class SQLCita 
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
	public SQLCita(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	//crear una cita
	public long crearCita(PersistenceManager pm, long id, Timestamp fechaHora, String estado, long  idEPS, long idPuntoVacunacion, long idUsuario) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darCita() + "(ID, FECHAHORA, ESTADO, IDEPS, IDPUNTOVACUNACION, IDUSUARIO) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(id, fechaHora, estado, idEPS, idPuntoVacunacion, idUsuario);
        return (long) q.executeUnique();
	}
	
	//modificar el estado de una cita
	public long actualizarEstadoCita(PersistenceManager pm, long id, long idUsuario, String estado) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + pv.darCita() + " SET ESTADO = ? WHERE ID = ? AND IDUSUARIO = ?");
	     q.setParameters(estado, id, idUsuario);
	     return (long) q.executeUnique();            
	}
	
	public Cita darCitaPorID(PersistenceManager pm, long idCita) 
	{
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pv.darCita() + " WHERE ID = ?");
		q.setResultClass(Cita.class);
		q.setParameters(idCita);
		return (Cita) q.executeUnique();
	}

	public List<BigDecimal> darIDCitasPorUsuario(PersistenceManager pm, long idUsuario)
	{
		String str = "";
		
		str += " SELECT ID ";
		str += " FROM cita"; 
		str += " WHERE idusuario = ?";
		
		Query q = pm.newQuery(SQL,str); 
		q.setParameters(idUsuario);
		return (List<BigDecimal>)q.executeList();
	}
	
	
	//encuentra el numero de citas en un rango de fechas
	
	public int numCitasEnFechaHora(PersistenceManager pm, Timestamp fechaI, Timestamp fechaF, long idPunto)
	{
		String str = "";
		
		str += " NVL(SELECT count(*),0)";
		str += " FROM cita";
		str += " WHERE idpuntovacunacion = ?";
		str += " AND fechahora >= ?";
		str += " AND fechahora <= ?";
		
		Query q = pm.newQuery(SQL, str);
		q.setParameters(idPunto, fechaI, fechaF); 
		
		return ((BigDecimal) q.executeUnique()).intValue(); 
	}
	
	
	public List<Cita> darCitasPorRangoFechaYPunto(PersistenceManager pm, Timestamp fechaI, Timestamp fechaF, long idPunto)
	{
		
		String str = "";
		
		str += " SELECT *";
		str += " FROM cita";
		str += " WHERE idpuntovacunacion = ?";
		str += " AND fechahora >= ?";
		str += " AND fechahora <= ?";
		
		Query q = pm.newQuery(SQL, str);
		q.setParameters(idPunto, fechaI, fechaF);
		q.setResultClass(Cita.class);
		return (List<Cita>) q.executeList(); 
		
	}
	
	
	


}
