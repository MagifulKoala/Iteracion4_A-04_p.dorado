/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
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
	public SQLUtil (PersistenciaVacuAndes pv)
	{
		this.pv = pv;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pv.darSeqVacuAndes()+ ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos (PersistenceManager pm)
	{
		
		Query darDosisVacuna = pm.newQuery(SQL, "DELETE FROM " + pv.darDosisVacuna());
		Query darCita = pm.newQuery(SQL, "DELETE FROM " + pv.darCita());
		Query darRol = pm.newQuery(SQL, "DELETE FROM " + pv.darRol());
		Query darUsuarioResidente = pm.newQuery(SQL, "DELETE FROM " + pv.darUsuarioResidente());
		Query darTieneVacuna = pm.newQuery(SQL, "DELETE FROM " + pv.darTieneVacuna());
		Query darPuntoVacunacion = pm.newQuery(SQL, "DELETE FROM " + pv.darPuntoVacunacion());
		Query darVacuna = pm.newQuery(SQL, "DELETE FROM " + pv.darVacuna());
		Query darProcesoVacunacion = pm.newQuery(SQL, "DELETE FROM " + pv.darProcesoVacunacion());
		Query darEPSRegional = pm.newQuery(SQL, "DELETE FROM " + pv.darEPSRegional());
		Query darEntradaPlan = pm.newQuery(SQL, "DELETE FROM " + pv.darEntradaPlan());
        Query darMinisterioSalud = pm.newQuery(SQL, "DELETE FROM " + pv.darMinisterioSalud());          
        
        
        
        long darDosisVacunaEliminados = (long) darDosisVacuna.executeUnique ();
        long darCitaEliminados = (long) darCita.executeUnique ();
        long darRolEliminados = (long) darRol.executeUnique ();
        long darProcesoVacunacionEliminados = (long) darProcesoVacunacion.executeUnique ();
        long darUsuarioResidenteEliminados = (long) darUsuarioResidente.executeUnique ();
        long darTieneVacunaEliminados = (long) darTieneVacuna.executeUnique ();
        long darPuntoVacunacionEliminados = (long) darPuntoVacunacion.executeUnique ();
        long darVacunaEliminados = (long) darVacuna.executeUnique ();
        long darEPSRegionalEliminados = (long) darEPSRegional.executeUnique ();
        long darEntradaPlanEliminados = (long) darEntradaPlan.executeUnique ();
        long darMinisterioSaludEliminados = (long) darMinisterioSalud.executeUnique ();
        
        
        
        
        
        return new long[] {darMinisterioSaludEliminados, darEntradaPlanEliminados, darEPSRegionalEliminados, darProcesoVacunacionEliminados, 
        		darVacunaEliminados, darPuntoVacunacionEliminados, darTieneVacunaEliminados,
        		darUsuarioResidenteEliminados,darRolEliminados, darCitaEliminados, darDosisVacunaEliminados};
	}

}
