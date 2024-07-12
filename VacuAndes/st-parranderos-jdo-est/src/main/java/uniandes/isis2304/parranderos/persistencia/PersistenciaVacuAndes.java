package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Cita;
import uniandes.isis2304.parranderos.negocio.DosisVacuna;
import uniandes.isis2304.parranderos.negocio.EPSRegional;
import uniandes.isis2304.parranderos.negocio.EntradaPlan;
import uniandes.isis2304.parranderos.negocio.MinisterioSalud;
import uniandes.isis2304.parranderos.negocio.ProcesoVacunacion;
import uniandes.isis2304.parranderos.negocio.PuntoVacunacion;
import uniandes.isis2304.parranderos.negocio.TieneVacuna;
import uniandes.isis2304.parranderos.negocio.UsuarioResidente;
import uniandes.isis2304.parranderos.negocio.Vacuna;

public class PersistenciaVacuAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaVacuAndes.class.getName());
	
	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaVacuAndes instance;
	
	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;
	
	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, MinisterioSalud, EntradaPlan, EPSRegional, Vacuna, PuntoVacunacion, TieneVacuna, UsuarioResidente,ProcesoVacunacion, Rol, Cita, DosisVacuna  
	 */
	private List <String> tablas;
	
	
	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaVacuAndes
	 */
	private SQLUtil sqlUtil;
	
	/**
	 * Atributo para el acceso a la tabla MinisterioSalud de la base de datos
	 */
	private SQLMinisterioSalud sqlMinisterioSalud;
	
	/**
	 * Atributo para el acceso a la tabla EntradaPlan de la base de datos
	 */
	private SQLEntradaPlan sqlEntradaPlan;
	
	/**
	 * Atributo para el acceso a la tabla EPSRegional de la base de datos
	 */
	private SQLEPSRegional sqlEPSRegional;
	
	/**
	 * Atributo para el acceso a la tabla ProcesoVacunacion de la base de datos
	 */
	private SQLProcesoVacunacion sqlProcesoVacunacion;
	
	/**
	 * Atributo para el acceso a la tabla Vacuna de la base de datos
	 */
	private SQLVacuna sqlVacuna;
	
	/**
	 * Atributo para el acceso a la tabla PuntoVacunacion de la base de datos
	 */
	private SQLPuntoVacunacion sqlPuntoVacunacion;
	
	/**
	 * Atributo para el acceso a la tabla TieneVacuna de la base de datos
	 */
	private SQLTieneVacuna sqlTieneVacuna;
	
	/**
	 * Atributo para el acceso a la tabla UsuarioResidente de la base de datos
	 */
	private SQLUsuarioResidente sqlUsuarioResidente;
	
	/**
	 * Atributo para el acceso a la tabla Rol de la base de datos
	 */
	private SQLRol sqlRol;
	
	/**
	 * Atributo para el acceso a la tabla Cita de la base de datos
	 */
	private SQLCita sqlCita;
	
	/**
	 * Atributo para el acceso a la tabla DosisVacuna de la base de datos
	 */
	private SQLDosisVacuna sqlDosisVacuna;
	
	
	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/
	
	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaVacuAndes()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("VacuAndes");		
		crearClasesSQL();
		
		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("vacuandes_sequence");
		tablas.add ("MinisterioSalud");
		tablas.add ("EntradaPlan");
		tablas.add ("EPSRegional");
		tablas.add ("Vacuna");
		tablas.add ("PuntoVacunacion");
		tablas.add ("TieneVacuna");
		tablas.add ("UsuarioResidente");
		tablas.add ("ProcesoVacunacion");
		tablas.add ("Rol");
		tablas.add ("Cita");
		tablas.add ("DosisVacuna");
	}
	
	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaVacuAndes (JsonObject tableConfig)
	{
		crearClasesSQL();
		tablas = leerNombresTablas (tableConfig);
		
		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}
	
	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaVacuAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaVacuAndes();
		}
		return instance;
	}
	
	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaVacuAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaVacuAndes(tableConfig);
		}
		return instance;
	}
	
	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}
	
	
	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		
		return resp;
	}
	
	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL()
	{
		sqlMinisterioSalud = new SQLMinisterioSalud(this);
		sqlEntradaPlan = new SQLEntradaPlan(this);
		sqlEPSRegional = new SQLEPSRegional(this);
		sqlVacuna = new SQLVacuna(this);
		sqlPuntoVacunacion = new SQLPuntoVacunacion(this);
		sqlTieneVacuna = new SQLTieneVacuna(this);
		sqlUsuarioResidente = new SQLUsuarioResidente(this);
		sqlProcesoVacunacion = new SQLProcesoVacunacion(this);
		sqlRol = new SQLRol(this);
		sqlCita = new SQLCita(this);
		sqlDosisVacuna = new SQLDosisVacuna(this);
		sqlUtil = new SQLUtil(this);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de VacuAndes
	 */
	public String darSeqVacuAndes ()
	{
		return tablas.get(0);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de MinisterioSalud
	 */
	public String darMinisterioSalud()
	{
		return tablas.get(1);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de EntradaPlan
	 */
	public String darEntradaPlan()
	{
		return tablas.get(2);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de EPSRegional
	 */
	public String darEPSRegional()
	{
		return tablas.get(3);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Vacuna
	 */
	public String darVacuna()
	{
		return tablas.get(4);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de PuntoVacunacion
	 */
	public String darPuntoVacunacion()
	{
		return tablas.get(5);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TieneVacuna
	 */
	public String darTieneVacuna()
	{
		return tablas.get(6);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de UsuarioResidente
	 */
	public String darUsuarioResidente()
	{
		return tablas.get(7);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de ProcesoVacunacion
	 */
	public String darProcesoVacunacion()
	{
		return tablas.get(8);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Rol
	 */
	public String darRol()
	{
		return tablas.get(9);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Cita
	 */
	public String darCita()
	{
		return tablas.get(10);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de DosisVacuna
	 */
	public String darDosisVacuna()
	{
		return tablas.get(11);
	}
	
	
	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval()
	{
        long resp = sqlUtil.nextval (pmf.getPersistenceManager());
        log.trace ("Generando secuencia: " + resp);
        return resp;
    }
	
	
	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar los Ministerios de Salud
	 *****************************************************************/
	
	public MinisterioSalud adicionarMinisterio(String priorizacionVacuna)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlMinisterioSalud.agregarMinisterioSalud(pm, id, priorizacionVacuna);
            tx.commit();
            
            log.trace ("Inserción de MinisterioSalud: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new MinisterioSalud(id, priorizacionVacuna); 
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	//da todos los ministerios
	
	public List<MinisterioSalud> darMinisteriosSalud()
	{
		return sqlMinisterioSalud.darMinisterioSalud(pmf.getPersistenceManager()); 
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las EPSRegionales
	 *****************************************************************/
	
	public EPSRegional adicionarEPSRegional(String nombre, int telefono, String region, long idMinisterio, int capacidadVacunas)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlEPSRegional.agregarEPSRegional(pm, id, nombre, telefono, region, idMinisterio, capacidadVacunas); 
            tx.commit();
            
            log.trace ("Inserción de EPSRegional: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new EPSRegional(id, nombre, telefono, region, idMinisterio,capacidadVacunas); 
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public EPSRegional darEPSRegionalPorID(long idEPS)
	{
		return (EPSRegional) sqlEPSRegional.darEPSPorID(pmf.getPersistenceManager(), idEPS); 
	}
	
	public int cantidadActualvacunasEPS(long idEPS)
	{
		return sqlEPSRegional.cantidadActualvacunasEPS(pmf.getPersistenceManager(), idEPS); 
	}
	
	
	public List<Object []> darVacunasSuminstradasPorEPS(Timestamp fechaInicio, Timestamp fechaFin)
	{
		
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlEPSRegional.darVacunasSuminstradasPorEPS(pmf.getPersistenceManager(), fechaInicio, fechaFin);  
		
        for ( Object tupla : tuplas)
        {
			 Object [] datos = (Object []) tupla;
			 
			 long idEPS = ((BigDecimal) datos [0]).longValue ();
			 long dosisSuministradas = ((BigDecimal) datos [1]).longValue ();
			 

			Object [] resp = new Object [2];
			resp[0] = idEPS;   	
			resp [1] = dosisSuministradas;
			
			respuesta.add(resp);
        }

		return respuesta;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Puntos de Vacunacion
	 *****************************************************************/
	
	public PuntoVacunacion adicionarPuntoVacunacion(String localizacion, int capacidadSimultanea, int capacidadTotalDiaria, String infraestructura, String tipo, long idEPS, String estado,  long capacidadTotal, long capacidadDosis)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlPuntoVacunacion.agregarPuntoVacunacion(pm, id, localizacion, capacidadSimultanea, capacidadTotalDiaria, infraestructura, tipo, idEPS, estado, capacidadTotal, capacidadDosis); 
            tx.commit();
            
            log.trace ("Inserción de PuntoVacunacion: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new PuntoVacunacion(id, localizacion, capacidadSimultanea, capacidadTotalDiaria, infraestructura, tipo, idEPS, estado, capacidadTotal, capacidadDosis); 
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public long  actualizarEstadoPuntoVacunacion( long idPuntoVacunacion, String estado)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlPuntoVacunacion.actualizarEstadoPuntoVacunacion(pm, idPuntoVacunacion, estado); 
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public PuntoVacunacion darPuntoVacunacionPorID(long idPuntoVacunacion)
	{
		return (PuntoVacunacion) sqlPuntoVacunacion.darPuntoVacunacionPorID(pmf.getPersistenceManager(), idPuntoVacunacion); 
	}
	
	public int cantidadActualPersonasPuntoVacunacion(long idPuntoVacunacion)
	{
		return sqlPuntoVacunacion.cantidadActualPersonasPuntoVacunacion(pmf.getPersistenceManager(), idPuntoVacunacion); 
	}
	
	public int cantidadActualvacunasPuntoVacunacion(long idPuntoVacunacion)
	{
		return sqlPuntoVacunacion.cantidadActualvacunasPuntoVacunacion(pmf.getPersistenceManager(), idPuntoVacunacion);
	}
	
	
	//RFC1
	
	public List<UsuarioResidente> darCiudadanosAtendisoPorPunto(long id, Timestamp fechaInicio, Timestamp fechaFin)
	{
	
		return sqlPuntoVacunacion.darCiudadanosAtendisoPorPunto(pmf.getPersistenceManager(), id, fechaInicio, fechaFin); 
	}
	
	//RFC2
	
	public List<Object []> dar20PuntosMasEfectivos(Timestamp fechaInicio, Timestamp fechaFin)
	{
		
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlPuntoVacunacion.darPuntosMasEfectivos(pmf.getPersistenceManager(), fechaInicio, fechaFin); 
		
        for ( Object tupla : tuplas)
        {
			 Object [] datos = (Object []) tupla;
			 
			 long id = ((BigDecimal) datos [0]).longValue ();
			 String localizacion = (String) datos [1];
			 int capacidadSimultanea = ((BigDecimal) datos [2]).intValue ();
			 int capacidadTotalDiaria = ((BigDecimal) datos [3]).intValue ();
			 String infraestructura = (String) datos [4];
			 String tipo = (String) datos [5];
			 long idEPS = ((BigDecimal) datos [6]).longValue ();
			 String estado = (String) datos [7];
			 int capacidadTotal = ((BigDecimal) datos [8]).intValue ();
			 int capacidadDosis = ((BigDecimal) datos [9]).intValue ();
			 int totalVacunas = ((BigDecimal) datos [10]).intValue ();
			 

			Object [] resp = new Object [2];
			resp[0] = new PuntoVacunacion(id, localizacion, capacidadSimultanea, capacidadTotalDiaria, infraestructura, tipo, idEPS, estado, capacidadTotal, capacidadDosis);  	
			resp [1] = totalVacunas;
			
			respuesta.add(resp);
        }

		return respuesta;
	}
	
	
	public List<Object []> dar20PuntosMenosEfectivos(Timestamp fechaInicio, Timestamp fechaFin)
	{
		
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlPuntoVacunacion.darPuntosMasEfectivos(pmf.getPersistenceManager(), fechaInicio, fechaFin); 
		
        for ( Object tupla : tuplas)
        {
			 Object [] datos = (Object []) tupla;
			 
			 long id = ((BigDecimal) datos [0]).longValue ();
			 String localizacion = (String) datos [1];
			 int capacidadSimultanea = ((BigDecimal) datos [2]).intValue ();
			 int capacidadTotalDiaria = ((BigDecimal) datos [3]).intValue ();
			 String infraestructura = (String) datos [4];
			 String tipo = (String) datos [5];
			 long idEPS = ((BigDecimal) datos [6]).longValue ();
			 String estado = (String) datos [7];
			 int capacidadTotal = ((BigDecimal) datos [8]).intValue ();
			 int capacidadDosis = ((BigDecimal) datos [9]).intValue ();
			 int totalVacunas = ((BigDecimal) datos [10]).intValue ();
			 

			Object [] resp = new Object [2];
			resp[0] = new PuntoVacunacion(id, localizacion, capacidadSimultanea, capacidadTotalDiaria, infraestructura, tipo, idEPS, estado, capacidadTotal, capacidadDosis);  	
			resp [1] = totalVacunas;
			
			respuesta.add(resp);
        }

		return respuesta;
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar los UsuariosResidentes
	 *****************************************************************/
	
	public UsuarioResidente adicionarUsuarioResidente(String nombre, int edad, int telefono, String condicionesMedicas, String profesion,
			int fase, int etapa, int prioridad, long idEPS, long idPuntoVacunacion, long idTrabaja)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlUsuarioResidente.agregarUsuarioResidente(pm, id, nombre, edad, telefono, condicionesMedicas, profesion, fase, etapa, prioridad, idEPS, idPuntoVacunacion, idTrabaja);
            tx.commit();
            
            log.trace ("Inserción de PuntoVacunacion: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new UsuarioResidente(id, nombre, edad, telefono, condicionesMedicas, profesion, fase, etapa, prioridad, idEPS, idPuntoVacunacion, idTrabaja);  
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	// retorna todos los usuarios vacunados dado un rango de fechas y un parametro de ordenamiento 
	public List<UsuarioResidente> consultarVacunados(Timestamp fechaI, Timestamp fechaF, String ordenamiento)
	{
		System.out.println("persistencia RFC10");
		return sqlUsuarioResidente.consultarVacunados(pmf.getPersistenceManager(), fechaI, fechaF, ordenamiento); 
	}
	
	
	public List<UsuarioResidente> consultarNoVacunados(Timestamp fechaI, Timestamp fechaF, String ordenamiento)
	{
		System.out.println("persistencia RFC11");
		return sqlUsuarioResidente.consultarNoVacunados(pmf.getPersistenceManager(), fechaI, fechaF, ordenamiento); 
	}
	
	public List<BigDecimal> darIdUsuariosEnPuntoVac(long idPunto)
	{
		return sqlUsuarioResidente.darIdUsuariosEnPuntoVac(pmf.getPersistenceManager(), idPunto); 
	}
	
	public UsuarioResidente darUsuarioPorID(long idUsuario)
	{
		return (UsuarioResidente) sqlUsuarioResidente.darUsuarioPorID(pmf.getPersistenceManager(), idUsuario); 
	}
	
	
	public long  actualizarPuntoVacunacionUsuarios( long idPuntoVacunacionViejo, long idPuntoVacunacionNuevo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlUsuarioResidente.actualizarPuntoVacunacionUsuarios(pm, idPuntoVacunacionViejo, idPuntoVacunacionNuevo); 
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public long  actualizarPuntoVacunacionUsuarioPorId( long idPuntoVacunacionNuevo, long idUsuario)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlUsuarioResidente.actualizarPuntoVacunacionUsuarioPorId(pm, idPuntoVacunacionNuevo, idUsuario); 
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public List<UsuarioResidente> darUsuarios()
	{
		return sqlUsuarioResidente.darUsuarios(pmf.getPersistenceManager()); 
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar el ProcesoVacunacion
	 *****************************************************************/
	
	//se agrega un proceso de vacunacion a un usuario especifico
	public ProcesoVacunacion adicionarProcesoVacunacion(int numDosis, int dosisAplicadas, int vacunado, int concentimiento, long idUsuario)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlProcesoVacunacion.agregarProcesoVacunacion(pm, id, numDosis, dosisAplicadas, vacunado, concentimiento, idUsuario); 
            tx.commit();
            
            log.trace ("Inserción de ProcesoVacunacion: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new ProcesoVacunacion(id, numDosis, dosisAplicadas, vacunado, concentimiento, idUsuario);  
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public ProcesoVacunacion darProcesoVacunaPorIDUsuario(long idUsuario)
	{
		return (ProcesoVacunacion) sqlProcesoVacunacion.darProcesoVacunaPorIDUsuario(pmf.getPersistenceManager(), idUsuario); 
	}
	
	
	//se actualiza la informacion en su totalidad de un proceso de vacunacion dado su identificador
	public long  actualizarProcesoVacunacion(long id, int numDosis, int dosisAplicadas, int vacunado, int concentimiento)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlProcesoVacunacion.modificarProcesoVacunacion(pm, id, numDosis, dosisAplicadas, vacunado, concentimiento); 
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	//RFC3 - mostrar indice vacunacion 
	public double mostrarIndiceVacunacion()
	{
		return sqlProcesoVacunacion.mostrarIndiceVacunacion(pmf.getPersistenceManager());
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las citas
	 *****************************************************************/
	
	//se agrega una cita a un usuario especifico
	
	public Cita adicionarCita(Timestamp fechaHora, String estado, long idEPS, long idPuntoVacunacion, long idUsuario)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlCita.crearCita(pm, id, fechaHora, estado, idEPS, idPuntoVacunacion, idUsuario);  
            tx.commit();
            
            log.trace ("Creacion de una cita: " + id + ": al usuario con id: " + idUsuario + "  " + tuplasInsertadas + " tuplas insertadas");
            return new Cita(id, fechaHora, estado, idEPS, idPuntoVacunacion, idUsuario);  
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public Cita darCitaPorID(long idCita)
	{
		return (Cita) sqlCita.darCitaPorID(pmf.getPersistenceManager(), idCita); 
	}
	
	public List<Cita> darCitasPorRangoFechaYPunto(Timestamp fechaI, Timestamp fechaF, long idPunto)
	{
		return sqlCita.darCitasPorRangoFechaYPunto(pmf.getPersistenceManager(),fechaI, fechaF, idPunto); 
	}
	
	public List<BigDecimal> darIDCitasPorUsuario(long idUsuario)
	{
		return sqlCita.darIDCitasPorUsuario(pmf.getPersistenceManager(), idUsuario); 
	}
	
	public int numCitasEnFechaHora(Timestamp fechaI, Timestamp fechaF, long idPunto)
	{
		return sqlCita.numCitasEnFechaHora(pmf.getPersistenceManager(), fechaI, fechaF, idPunto); 
	}
	
	
	//se actualiza el estado de una cita especifica
	public long  actualizarCita(long id, long idUsuario, String estado)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlCita.actualizarEstadoCita(pm, id, idUsuario, estado);  
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las Vacunas
	 *****************************************************************/
	//se agrega un proceso de vacunacion a un usuario especifico
	public Vacuna adicionarVacuna(String nombre, String informacion, int numDosis, String condiciones, int cantidad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlVacuna.agregarVacuna(pm, id, nombre, informacion, numDosis, condiciones, cantidad);  
            tx.commit();
            
            log.trace ("Inserción de Vacuna: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            return new Vacuna(id, nombre, informacion, numDosis, condiciones, cantidad);  
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	public Vacuna darVacunaPorID(long idVacuna)
	{
		return (Vacuna) sqlVacuna.darVacunaPorID(pmf.getPersistenceManager(), idVacuna); 
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las dosis de la vacuna
	 *****************************************************************/
	
	public DosisVacuna adicionarDosisVacuna(int aplicada, long idUsuario, long idPuntoVacunacion, long idVacuna)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();

            long tuplasInsertadas = sqlDosisVacuna.agregarDosisVacuna(pm, id, aplicada, idUsuario, idPuntoVacunacion, idVacuna);   
            tx.commit();
            
            log.trace ("Inserción de la dosis de la vacuna: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
            
            return new DosisVacuna(id, aplicada, idUsuario, idPuntoVacunacion, idVacuna); 
     
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public DosisVacuna darDosisVacunaPorID(long idDosis)
	{
		return (DosisVacuna) sqlDosisVacuna.darDosisVacunaPorID(pmf.getPersistenceManager(), idDosis); 
	}
	
	
	public DosisVacuna darDosisVacunaPorIDUsuario(long idUsuario)
	{
		return (DosisVacuna) sqlDosisVacuna.darDosisVacunaPorIdUsuario(pmf.getPersistenceManager(), idUsuario); 
	}
	
	
	//se actualiza el estadode una dosis suministrada
	public long  actualizarDosisVacunaAplicada(long idDosis, long idUsuario)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlDosisVacuna.actualizarDosisVacunaAplicada(pm, idDosis, idUsuario);  
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	
	//se actualiza el el punto de vacunacion de una dosis especifica dado idDosis
	public long  modificarPuntoDeVacunacionDosisPorID( long idPuntoVacunacion, long idDosis)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long resp = sqlDosisVacuna.modificarPuntoDeVacunacionDosisPorID(pm, idPuntoVacunacion, idDosis); 
            tx.commit();
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
            return -1;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	public List<BigDecimal> darDosisPorVacuna(long idVacuna)
	{
		return sqlDosisVacuna.darDosisPorVacuna(pmf.getPersistenceManager(), idVacuna); 
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las Entradas del Plan
	 *****************************************************************/
	
	
	public EntradaPlan adicionarEntradaPlan(int fase, int etapa, Timestamp fechaInicio, Timestamp fechaFin, String condiciones, long idMinisterio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long id = nextval ();
            long tuplasInsertadas = sqlEntradaPlan.crearEntradaPlan(pm, id, fase, etapa, fechaInicio, fechaFin, condiciones, idMinisterio); 
            tx.commit();
            
            log.trace ("Creacion de la entrada del plan: " + id + "al minsterio" + idMinisterio);
            return new EntradaPlan(id, fase, etapa, fechaInicio, fechaFin, condiciones, idMinisterio);   
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar la relacion TieneVacuna
	 *****************************************************************/
	public TieneVacuna adicionarTieneVacuna(long idVacuna, long idEPS)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long tuplasInsertadas = sqlTieneVacuna.agregarTieneVacuna(pm, idVacuna, idEPS);  
            tx.commit();
            
            log.trace ("Inserción de relacion tieneVacuna: " + idVacuna + "con eps" + idEPS +"  --> " + tuplasInsertadas + " tuplas insertadas");
            return new TieneVacuna(idVacuna, idEPS); 
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return null;
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
	}
	
	
	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de VacuAndes
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas 
	 */
	public long [] limpiarVacuAndes()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx=pm.currentTransaction();
        try
        {
            tx.begin();
            long [] resp = sqlUtil.limpiarParranderos(pm);
            tx.commit ();
            log.info ("Borrada la base de datos");
            return resp;
        }
        catch (Exception e)
        {
//        	e.printStackTrace();
        	log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
        	return new long[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
		
	}
	
	
	
	
	
	

}
