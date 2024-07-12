package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.PuntoVacunacion;
import uniandes.isis2304.parranderos.negocio.UsuarioResidente;

class SQLUsuarioResidente 
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
	public SQLUsuarioResidente(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	//agregar usuario residente
	public long agregarUsuarioResidente(PersistenceManager pm, long id, String nombre, int edad, int telefono, String condicionesMedicas, String profesion, int fase, int etapa, int prioridad, long idEPS, long idPuntoVacunacion, long idTrabaja) 
	{
		
		if(idTrabaja == -1)
		{
	        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darUsuarioResidente() + "(ID, NOMBRE, EDAD, TELEFONO, CONDICIONESMEDICAS, PROFESION, FASE, ETAPA, PRIORIDAD, IDEPS, IDPUNTOVACUNACION) values (?, ?, ?, ?,?, ?, ?, ?,?, ?, ?)");
	        q.setParameters(id, nombre, edad, telefono,condicionesMedicas, profesion,fase, etapa, prioridad,idEPS, idPuntoVacunacion, idTrabaja);
	        return (long) q.executeUnique();
			
		}else
		{
	        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darUsuarioResidente() + "(ID, NOMBRE, EDAD, TELEFONO, CONDICIONESMEDICAS, PROFESION, FASE, ETAPA, PRIORIDAD, IDEPS, IDPUNTOVACUNACION, IDTRABAJA) values (?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?)");
	        q.setParameters(id, nombre, edad, telefono,condicionesMedicas, profesion,fase, etapa, prioridad,idEPS, idPuntoVacunacion, idTrabaja);
	        return (long) q.executeUnique();
		}

	}
	
	
	public UsuarioResidente darUsuarioPorID(PersistenceManager pm, long idUsuario) 
	{
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pv.darUsuarioResidente() + " WHERE id = ?");
		q.setResultClass(UsuarioResidente.class);
		q.setParameters(idUsuario);
		return (UsuarioResidente) q.executeUnique();
	}
	
	
	//
	public long actualizarPuntoVacunacionUsuarios(PersistenceManager pm, long idPuntoVacunacionViejo, long idPuntoVacunacionNuevo) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + pv.darUsuarioResidente()+ " SET IDPUNTOVACUNACION = ? WHERE IDPUNTOVACUNACION = ?");
	     q.setParameters(idPuntoVacunacionNuevo, idPuntoVacunacionViejo);
	     return (long) q.executeUnique();            
	}
	
	
	public long actualizarPuntoVacunacionUsuarioPorId(PersistenceManager pm, long idPuntoVacunacionNuevo, long idUsuario)	{
		
		System.out.println("sqlUsuario actualizarPuntiPorId");
		 Query q = pm.newQuery(SQL, "UPDATE " + pv.darUsuarioResidente()+ " SET IDPUNTOVACUNACION = ? WHERE ID = ?");
	     q.setParameters(idPuntoVacunacionNuevo, idUsuario);
	     return (long) q.executeUnique();            
	}
	
	
	//dar Id de todos los usuarios en un punto de vacunacion
	
	public List<BigDecimal> darIdUsuariosEnPuntoVac(PersistenceManager pm, long idPunto)
	{
		String str = "";
		
		str += " SELECT ID ";
		str += " FROM UsuarioResidente"; 
		str += " WHERE IDPUNTOVACUNACION = ?";
		
		Query q = pm.newQuery(SQL,str); 
		q.setParameters(idPunto);
		
		return (List<BigDecimal>)q.executeList();
	}
	
	//listar todos los usuarios residentes: 
	
	public List<UsuarioResidente> darUsuarios(PersistenceManager pm)
	{
		String str = "SELECT u2.ID, u2.NOMBRE , u2.EDAD ,u2.TELEFONO ,u2.CONDICIONESMEDICAS ,u2.PROFESION ,u2.FASE ,u2.ETAPA ,u2.PRIORIDAD ,u2.IDEPS ,u2.IDPUNTOVACUNACION ,NVL(u2.IDTRABAJA, -1) AS IDTRABAJA FROM USUARIORESIDENTE u2";
		
		Query q = pm.newQuery(SQL, str);
		q.setResultClass(UsuarioResidente.class);
		return (List<UsuarioResidente>) q.executeList(); 
		
	}
	
	// RFC10
	
	/**
	 * consulta la informacion de todos los usuarios vacunados dado un rango de fechas y un parametro de ordenamiento 
	 * @param pm manejador de la persistencia
	 * @param fechaI fecha inicial del rango de busqueda
	 * @param fechaF fecha final del rango de busqueda
	 * @param ordenamiento parametro de ordenamiento de la informacion
	 * @return lista de usuarios vacunados con su respectiva informacion 
	 */
	public List<UsuarioResidente> consultarVacunados(PersistenceManager pm, Timestamp fechaI, Timestamp fechaF, String ordenamiento)
	{
		String str = ""; 
		
		str += " SELECT u2.ID, u2.NOMBRE , u2.EDAD ,u2.TELEFONO ,u2.CONDICIONESMEDICAS ,u2.PROFESION ,u2.FASE ,u2.ETAPA ,u2.PRIORIDAD ,u2.IDEPS ,u2.IDPUNTOVACUNACION ,NVL(u2.IDTRABAJA, -1) as IDTRABAJA";
		str += " FROM usuarioResidente u2, ";
		str += " (";
		str += " SELECT cita.idUsuario AS idUsuario";
		str += " FROM cita";
		str += " WHERE cita.FECHAHORA >= ?";
		str += " AND cita.FECHAHORA <= ?";
		str += " AND cita.estado = 'FINALIZADA'";
		str += " )tabla2";
		str += " WHERE u2.id = tabla2.idUsuario";
		str += " ORDER BY u2." + ordenamiento;
		
		Query q = pm.newQuery(SQL, str);
		q.setParameters(fechaI, fechaF); 
		q.setResultClass(UsuarioResidente.class);
		
		return (List<UsuarioResidente>) q.executeList(); 
		
	}
	
	
	
	public List<UsuarioResidente> consultarNoVacunados(PersistenceManager pm, Timestamp fechaI, Timestamp fechaF, String ordenamiento)
	{
		String str = ""; 
		
		str += " SELECT u2.ID, u2.NOMBRE , u2.EDAD ,u2.TELEFONO ,u2.CONDICIONESMEDICAS ,u2.PROFESION ,u2.FASE ,u2.ETAPA ,u2.PRIORIDAD ,u2.IDEPS ,u2.IDPUNTOVACUNACION ,NVL(u2.IDTRABAJA, -1) as IDTRABAJA";
		str += " FROM usuarioResidente u2, ";
		str += " (";
		str += " SELECT cita.idUsuario AS idUsuario";
		str += " FROM cita";
		str += " WHERE cita.FECHAHORA >= ?";
		str += " AND cita.FECHAHORA <= ?";
		str += " AND cita.estado != 'FINALIZADA'";
		str += " )tabla2";
		str += " WHERE u2.id = tabla2.idUsuario";
		str += " ORDER BY u2." + ordenamiento;
		
		Query q = pm.newQuery(SQL, str);
		q.setParameters(fechaI, fechaF); 
		q.setResultClass(UsuarioResidente.class);
		
		return (List<UsuarioResidente>) q.executeList(); 
		
	}

}
