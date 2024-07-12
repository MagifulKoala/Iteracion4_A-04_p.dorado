package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.DosisVacuna;
import uniandes.isis2304.parranderos.negocio.ProcesoVacunacion;

class SQLDosisVacuna 
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
	public SQLDosisVacuna(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	
	public long agregarDosisVacuna(PersistenceManager pm, long id, int aplicada, long idUsuario, long idPuntoVacunacion, long idVacuna)
	{

		if(idUsuario == -1 && idPuntoVacunacion == -1)
		{
			Query q = pm.newQuery(SQL,"INSERT INTO "+ pv.darDosisVacuna() +" (ID, APLICADA, IDUSUARIO, IDPUNTOVACUNACION, IDVACUNA) VALUES(?, ?, null, null ,?)");
			q.setParameters(id, aplicada, idVacuna);
			return (long) q.executeUnique();
			
		}
		else if(idPuntoVacunacion == -1)
		{
			
			Query q = pm.newQuery(SQL,"INSERT INTO "+ pv.darDosisVacuna() +" (ID, APLICADA, IDUSUARIO, IDPUNTOVACUNACION, IDVACUNA) VALUES(?, ?, ?, null ,?)");
			q.setParameters(id, aplicada, idUsuario, idVacuna);
			return (long) q.executeUnique();
			
		}
		else if(idUsuario == -1)	
		{
			Query q = pm.newQuery(SQL,"INSERT INTO "+ pv.darDosisVacuna() +" (ID, APLICADA, IDUSUARIO, IDPUNTOVACUNACION, IDVACUNA) VALUES(?, ?, null, ? ,?)");
			q.setParameters(id, aplicada, idPuntoVacunacion, idVacuna);
			return (long) q.executeUnique();
			
		}
		else
		{
			Query q = pm.newQuery(SQL,"INSERT INTO "+ pv.darDosisVacuna() +" (ID, APLICADA, IDUSUARIO, IDPUNTOVACUNACION, IDVACUNA) VALUES(?, ?, ?, ? ,?)");
			q.setParameters(id, aplicada, idUsuario, idPuntoVacunacion, idVacuna);
			return (long) q.executeUnique();
		}
		
	}
	
	
	public DosisVacuna darDosisVacunaPorIdUsuario(PersistenceManager pm, long idUsuario) 
	{
		
		Query q = pm.newQuery(SQL, "SELECT ID,APLICADA, NVL(IDUSUARIO,-1)  AS IDUSUARIO , NVL(IDPUNTOVACUNACION,-1) AS IDPUNTOVACUNACION, IDVACUNA FROM " + pv.darDosisVacuna() + " WHERE IDUSUARIO = ?");
		q.setResultClass(DosisVacuna.class);
		q.setParameters(idUsuario);
		return (DosisVacuna) q.executeUnique();
	}
	
	
	public DosisVacuna darDosisVacunaPorID(PersistenceManager pm, long idDosis) 
	{
		
		Query q = pm.newQuery(SQL, "SELECT ID,APLICADA, NVL(IDUSUARIO,-1)  AS IDUSUARIO , NVL(IDPUNTOVACUNACION,-1) AS IDPUNTOVACUNACION, IDVACUNA FROM " + pv.darDosisVacuna() + " WHERE ID = ?");
		q.setResultClass(DosisVacuna.class);
		q.setParameters(idDosis);
		return (DosisVacuna) q.executeUnique();
	}
	
	
	public long actualizarDosisVacunaAplicada(PersistenceManager pm, long idDosis, long idUsuario) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + pv.darDosisVacuna()+ " SET IDPUNTOVACUNACION = NULL, APLICADA = 1, IDUSUARIO = ? WHERE ID = ?");
	     q.setParameters(idUsuario,idDosis);
	     return (long) q.executeUnique();            
	}
	
	public long modificarPuntoDeVacunacionDosisPorID(PersistenceManager pm, long idPuntoVacunacion, long idDosis) 
	{
		System.out.println("id punto es :"  + idPuntoVacunacion + " id Dosis es" + idDosis);
		 Query q = pm.newQuery(SQL, "UPDATE " + pv.darDosisVacuna() + " SET IDPUNTOVACUNACION = ? WHERE ID = ?");
	     q.setParameters(idPuntoVacunacion, idDosis);
	     return (long) q.executeUnique();            
	}
	
	
	public List<BigDecimal> darDosisPorVacuna(PersistenceManager pm, long idVacuna)
	{
		String str = "";
		
		str += " SELECT d.id ";
		str += " FROM dosisVacuna d"; 
		str += " WHERE d.IDVACUNA = ?";
		
		Query q = pm.newQuery(SQL,str); 
		q.setParameters(idVacuna);
		
		return (List<BigDecimal>)q.executeList();
	}
}
