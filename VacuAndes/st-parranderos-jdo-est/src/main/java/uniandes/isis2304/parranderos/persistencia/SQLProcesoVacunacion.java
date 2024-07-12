package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.ProcesoVacunacion;
import uniandes.isis2304.parranderos.negocio.PuntoVacunacion;

class SQLProcesoVacunacion 
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
	public SQLProcesoVacunacion(PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	
	//agregar proceso de vacunacion
	public long agregarProcesoVacunacion(PersistenceManager pm, long id, int numDosis, int dosisAplicadas, int vacunado, int concentimiento, long idUsuario) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pv.darProcesoVacunacion() + "(ID, NUMDOSIS, DOSISAPLICADAS, VACUNADO, CONCENTIMIENTO, IDUSUARIO) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(id, numDosis, dosisAplicadas, vacunado, concentimiento, idUsuario);
        return (long) q.executeUnique();
	}
	
	//modificar dosisAplicadas
	public long modificarDosisAplicadas(PersistenceManager pm, long id, int dosisAplicadas) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + pv.darProcesoVacunacion() + " SET DOSISAPLICADAS = ? WHERE id = ?");
	     q.setParameters(dosisAplicadas, id);
	     return (long) q.executeUnique();            
	}
	
	//modificar todo el proceso de vacunacion dado el id del proceso a modificar
	public long modificarProcesoVacunacion(PersistenceManager pm, long id, int numDosis, int dosisAplicadas, int vacunado, int concentimiento) 
	{
		 Query q = pm.newQuery(SQL, "UPDATE " + pv.darProcesoVacunacion() + " SET NUMDOSIS = ?, DOSISAPLICADAS = ?, VACUNADO = ?, CONCENTIMIENTO = ?   WHERE id = ?");
	     q.setParameters(numDosis, dosisAplicadas, vacunado, concentimiento, id);
	     return (long) q.executeUnique();            
	}
	
	
	public ProcesoVacunacion darProcesoVacunaPorIDUsuario(PersistenceManager pm, long idUsuario) 
	{
		
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pv.darProcesoVacunacion() + " WHERE IDUSUARIO = ?");
		q.setResultClass(ProcesoVacunacion.class);
		q.setParameters(idUsuario);
		return (ProcesoVacunacion) q.executeUnique();
	}
	
	//RFC3
	
	public double mostrarIndiceVacunacion(PersistenceManager pm)
	{
		 Query q = pm.newQuery(SQL, "SELECT COUNT(p.ID) FROM "+pv.darProcesoVacunacion() +" p");
	     Long total = ((BigDecimal) q.executeUnique()).longValue ();
	     System.out.println(total);
	     
	     Query q2 = pm.newQuery(SQL, "SELECT COUNT(p.ID) FROM PROCESOVACUNACION p WHERE p.VACUNADO = 1");
	     Long totalVacunados = ((BigDecimal) q2.executeUnique()).longValue ();; 
	     System.out.println(""+totalVacunados);
	     
	     return ((double)totalVacunados/(double)total)*100.00;
	     
	}

}
