package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.PuntoVacunacion;
import uniandes.isis2304.parranderos.negocio.UsuarioResidente;
import uniandes.isis2304.parranderos.negocio.Vacuna;

class SQLPuntoVacunacion 
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
	public SQLPuntoVacunacion(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	
	//crear un punto de vacunacion
	public long agregarPuntoVacunacion(PersistenceManager pm, long id, String localizacion, int capacidadSimultanea, int capacidadTotalDiaria, String infraestructura, String tipo, long idEPS, String estado, long capacidadTotal, long capacidadDosis) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darPuntoVacunacion() + "(ID, LOCALIZACION, CAPACIDADSIMULTANEA, CAPACIDADTOTALDIARIA, INFRAESTRUCTURA, TIPO, IDEPS, ESTADO,CAPACIDADTOTAL, CAPACIDADDOSIS) values (?, ?, ?, ?, ?, ?, ?,?,?,?)");
        q.setParameters(id, localizacion, capacidadSimultanea,capacidadTotalDiaria, infraestructura, tipo, idEPS, estado, capacidadTotal, capacidadDosis);
        return (long) q.executeUnique();
	}
	
	//da el punto de vacunacion
	public PuntoVacunacion darPuntoVacunacionPorID(PersistenceManager pm, long idPuntoVacunacion) 
	{
		System.out.println("entra a dar punto por id");
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pv.darPuntoVacunacion() + " WHERE id = ?");
		q.setResultClass(PuntoVacunacion.class);
		q.setParameters(idPuntoVacunacion);
		return (PuntoVacunacion) q.executeUnique();
	}
	
	
	public long actualizarEstadoPuntoVacunacion(PersistenceManager pm, long idPuntoVacunacion, String estado) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + pv.darPuntoVacunacion()+ " SET ESTADO = ? WHERE ID = ?");
	     q.setParameters(estado, idPuntoVacunacion);
	     return (long) q.executeUnique();            
	}
	
	//da el total de personas registradas en el punto de vacunacion en el momento
	public int cantidadActualPersonasPuntoVacunacion(PersistenceManager pm, long idPuntoVacunacion) 
	{
		System.out.println("entra a cantidad");
		
		String str = "";
		
		str += " SELECT NVL(count(*),0)";
		str += " FROM usuarioResidente";
		str += " WHERE idpuntovacunacion = ? ";
		str += " OR IDTRABAJA = ? ";
		
		
		Query q = pm.newQuery(SQL, str); 
		q.setParameters(idPuntoVacunacion, idPuntoVacunacion);
		return ((BigDecimal) q.executeUnique()).intValue(); 
	}
	
	
	//RFC1 y RFC2
	
	public List<UsuarioResidente> darCiudadanosAtendisoPorPunto(PersistenceManager pm, long id, Timestamp fechaInicio, Timestamp fechaFin)
	{
		String q = "SELECT u2.ID, u2.NOMBRE , u2.EDAD ,u2.TELEFONO ,u2.CONDICIONESMEDICAS ,u2.PROFESION ,u2.FASE ,u2.ETAPA ,u2.PRIORIDAD ,u2.IDEPS ,u2.IDPUNTOVACUNACION ,NVL(u2.IDTRABAJA, -1) AS IDTRABAJA ";  
				q += " FROM  CITA c, USUARIORESIDENTE u2, PUNTOVACUNACION p2";
				q += " WHERE c.IDUSUARIO = u2.ID";
				q += " AND c.IDPUNTOVACUNACION = p2.ID"; 
				q += " AND p2.ID = ?";
				q += " AND c.FECHAHORA >= ?";
				q += " AND C.FECHAHORA <= ?";
				
		Query query = pm.newQuery(SQL,q);
		query.setParameters(id, fechaInicio, fechaFin); 
		
		query.setResultClass(UsuarioResidente.class);
		return (List<UsuarioResidente>)query.executeList(); 
				
	}
	
	
	public List<Object> darPuntosMasEfectivos(PersistenceManager pm, Timestamp fechaInicio, Timestamp fechaFin)
	{
		String sql1 = "SELECT p.id AS id, COUNT(c.ID) AS totalVacunas";
		sql1 += " FROM CITA c, PUNTOVACUNACION p";
		sql1 += " WHERE c.IDPUNTOVACUNACION = p.ID";
		sql1 += " AND c.FECHAHORA >= ?";
		sql1 += " AND C.FECHAHORA <= ?";
		sql1 += " AND c.ESTADO = 'FINALIZADA'";
		sql1 += " GROUP BY p.ID";
		sql1 += " ORDER BY COUNT(c.ID) DESC";
		sql1 += " FETCH FIRST 20 row ONLY";
		
		
		String q = "SELECT p.ID , p.LOCALIZACION, p.CAPACIDADSIMULTANEA , p.CAPACIDADTOTALDIARIA , p.INFRAESTRUCTURA , p.TIPO , p.IDEPS, p.ESTADO, p.CAPACIDADTOTAL, p.CAPACIDADDOSIS, tabla2.totalVacunas"; 
		q += " FROM PUNTOVACUNACION p,";
		q += " (";
		q += sql1;
		q += " )tabla2";
		q += " WHERE tabla2.id = p.id";
		
		Query query = pm.newQuery(SQL,q);
		query.setParameters(fechaInicio, fechaFin);
		return query.executeList(); 
	}
	
	
	public List<Object> darPuntosMenosEfectivos(PersistenceManager pm, Timestamp fechaInicio, Timestamp fechaFin)
	{
		String sql1 = "SELECT p.id AS id, COUNT(c.ID) AS totalVacunas";
		sql1 += " FROM CITA c, PUNTOVACUNACION p";
		sql1 += " WHERE c.IDPUNTOVACUNACION = p.ID";
		sql1 += " AND c.FECHAHORA >= ?";
		sql1 += " AND C.FECHAHORA <= ?";
		sql1 += " AND c.ESTADO = 'FINALIZADA'";
		sql1 += " GROUP BY p.ID";
		sql1 += " ORDER BY COUNT(c.ID) ASC";
		sql1 += " FETCH FIRST 20 row ONLY";
		
		
		String q = "SELECT p.ID , p.LOCALIZACION, p.CAPACIDADSIMULTANEA , p.CAPACIDADTOTALDIARIA , p.INFRAESTRUCTURA , p.TIPO , p.IDEPS, p.ESTADO, p.CAPACIDADTOTAL, p.CAPACIDADDOSIS, tabla2.totalVacunas"; 
		q += " FROM PUNTOVACUNACION p,";
		q += " (";
		q += sql1;
		q += " )tabla2";
		q += " WHERE tabla2.id = p.id";
		
		Query query = pm.newQuery(SQL,q);
		query.setParameters(fechaInicio, fechaFin);
		return query.executeList(); 
	}
	
	
	//calcula la cantidad actual de dosis en el punto de vacunacion
	
	public int cantidadActualvacunasPuntoVacunacion(PersistenceManager pm, long idPuntoVacunacion) 
	{
		String str = "";
		
		str += " SELECT NVL(count(d.id),0) ";
		str += " FROM dosisvacuna d";
		str += " WHERE d.IDPUNTOVACUNACION = ?";
		
		
		Query q = pm.newQuery(SQL, str); 
		q.setParameters(idPuntoVacunacion);
		return ((BigDecimal) q.executeUnique()).intValue(); 
	}

}
