package uniandes.isis2304.parranderos.negocio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import jdk.internal.org.jline.utils.Log;
import uniandes.isis2304.parranderos.persistencia.PersistenciaVacuAndes;

public class VacuAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(VacuAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaVacuAndes pv;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public VacuAndes()
	{
		pv = PersistenciaVacuAndes.getInstance ();
	}
	
	/**
	 * El constructor que recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public VacuAndes(JsonObject tableConfig)
	{
		pv = PersistenciaVacuAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pv.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Ministerios de Salud
	 *****************************************************************/
	
	public MinisterioSalud adicionarMinisterioSalud (String priorizacionVacuna)
	{
        log.info ("Adicionando Ministerio de salud");
        MinisterioSalud ministerioSalud = pv.adicionarMinisterio(priorizacionVacuna); 		
        log.info ("Adicionando Ministeriode salud: " + ministerioSalud.getId());
        return ministerioSalud;
	}
	
	
	public List<MinisterioSalud> darMinisteriosSalud()
	{
		log.info("listando Ministerios de salud");
		List<MinisterioSalud> ministerios = pv.darMinisteriosSalud();  
		Log.info("listando Ministerios :" + ministerios.size() + "Ministerios existentes" );
		
		return ministerios; 
	}
	
	
	public List<VOMinisterioSalud> darVOMinisterioSalud()
	{
        log.info ("Generando los VO de los Ministerios");
         List<VOMinisterioSalud> voMinisterios = new LinkedList<VOMinisterioSalud>();
        for (MinisterioSalud ministerios : pv.darMinisteriosSalud())
        {
        	voMinisterios.add(ministerios);
        }
        log.info ("Generando los VO de los Ministerios: " + voMinisterios.size() + " Ministerios existentes");
       return voMinisterios;
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las EPSRegionales
	 *****************************************************************/
	
	public EPSRegional adicionarEPSRegional(String nombre, int telefono, String region, long idMinisterio, int capacidadVacunas)
	{
        log.info ("Adicionando EPSRegional");
        EPSRegional epsRegional = pv.adicionarEPSRegional(nombre, telefono, region, idMinisterio, capacidadVacunas); 		
        log.info ("Adicionando EPSRegional: " + nombre);
        return epsRegional;
	}
	
	public EPSRegional darEPSRegionalPorID(long idEPS)
	{
		EPSRegional eps = pv.darEPSRegionalPorID(idEPS); 
		return eps; 
	}
	
	public int cantidadActualvacunasEPS(long idEPS)
	{
		return pv.cantidadActualvacunasEPS(idEPS); 
	}
	
	
	public List<Object []> darVacunasSuminstradasPorEPS(Timestamp fechaInicio, Timestamp fechaFin)
	{
        List<Object []> tuplas = pv.darVacunasSuminstradasPorEPS(fechaInicio, fechaFin); 
        return tuplas;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los Puntos de Vacunacion
	 *****************************************************************/
	
	public PuntoVacunacion adicionarPuntoVacuncaion(String localizacion, int capacidadSimultanea, int capacidadTotalDiaria, String infraestructura, String tipo, long idEPS, String estado, long capacidadTotal, long capacidadDosis)
	{
        log.info ("Adicionando PuntoVacunacion");
        PuntoVacunacion puntoVacunacion = pv.adicionarPuntoVacunacion(localizacion, capacidadSimultanea, capacidadTotalDiaria, infraestructura, tipo, idEPS, estado, capacidadTotal, capacidadDosis); 		
        log.info ("Adicionando PuntoVacunacion: " + puntoVacunacion.getId());
        return puntoVacunacion;
	}
	
	public Long actualizarEstadoPuntoVacunacion( long idPuntoVacunacion, String estado)
	{
		Long puntoActualizado = pv.actualizarEstadoPuntoVacunacion(idPuntoVacunacion, estado); 
        return puntoActualizado; 
	}
	
	public PuntoVacunacion darPuntoVacunacionPorID(long idPuntoVacunacion)
	{
		PuntoVacunacion punto = pv.darPuntoVacunacionPorID(idPuntoVacunacion);  
		return punto; 
	}
	
	public int cantidadActualPersonasPuntoVacunacion(long idPuntoVacunacion)
	{
		return pv.cantidadActualPersonasPuntoVacunacion(idPuntoVacunacion); 
	}
	
	public int cantidadActualvacunasPuntoVacunacion(long idPuntoVacunacion)
	{
		return pv.cantidadActualvacunasPuntoVacunacion(idPuntoVacunacion); 
	}
	
	public List<UsuarioResidente> darCiudadanosAtendisoPorPunto(long id, Timestamp fechaInicio, Timestamp fechaFin)
	{
        log.info ("Listando Ciudadanos Atendidos por punto");
        List<UsuarioResidente> usuarios = pv.darCiudadanosAtendisoPorPunto(id, fechaInicio, fechaFin); 
        log.info ("Listando Ciudadanos Atendidos por punto: " + usuarios.size() + " usuarios existentes");
        return usuarios;
	}
	
	public List<Object []> dar20PuntosMasEfectivos(Timestamp fechaInicio, Timestamp fechaFin)
	{
        log.info ("Listando 20PuntosMasEfectivos");
        List<Object []> tuplas = pv.dar20PuntosMasEfectivos(fechaInicio, fechaFin); 
        log.info ("Listando 20PuntosMasEfectivos. Estado: Listo!");
        return tuplas;
	}
	
	
	public List<Object []> dar20PuntosMenosEfectivos(Timestamp fechaInicio, Timestamp fechaFin)
	{
        log.info ("Listando 20PuntosMasEfectivos");
        List<Object []> tuplas = pv.dar20PuntosMenosEfectivos(fechaInicio, fechaFin); 
        log.info ("Listando 20PuntosMasEfectivos. Estado: Listo!");
        return tuplas;
	}
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar los UsuarioResidentes
	 *****************************************************************/
	
	public UsuarioResidente adicionarUsuarioResidente(String nombre, int edad, int telefono, String condicionesMedicas, String profesion,
			int fase, int etapa, int prioridad, long idEPS, long idPuntoVacunacion, long idTrabaja)
	{
        log.info ("Adicionando UsuarioResidente");
        UsuarioResidente usuarioResidente = pv.adicionarUsuarioResidente(nombre, edad, telefono, condicionesMedicas, profesion, fase, etapa, prioridad, idEPS, idPuntoVacunacion, idTrabaja);  		
        log.info ("Adicionando UsuarioResidente: " + nombre);
        return usuarioResidente;
	}
	
	public Long actualizarPuntoVacunacionUsuarios( long idPuntoVacunacionViejo, long idPuntoVacunacionNuevo)
	{
		Long usuariosActualizados = pv.actualizarPuntoVacunacionUsuarios(idPuntoVacunacionViejo, idPuntoVacunacionNuevo); 
        return usuariosActualizados; 
	}
	
	
	public Long actualizarPuntoVacunacionUsuarioPorId( long idPuntoVacunacionNuevo, long idUsuario)
	{
		Long usuariosActualizados = pv.actualizarPuntoVacunacionUsuarioPorId(idPuntoVacunacionNuevo, idUsuario); 
        return usuariosActualizados; 
	}
	
	
	public UsuarioResidente darUsuarioPorID(long idUsuario)
	{
		UsuarioResidente usuario = pv.darUsuarioPorID(idUsuario);  
		return usuario; 
	}
	
	
	
	public List<BigDecimal> darIdUsuariosEnPuntoVac(long idPunto)
	{

		List<BigDecimal> dosis = pv.darIdUsuariosEnPuntoVac(idPunto); 
		return dosis; 
	}
	
	
	public List<UsuarioResidente> darUsuarios()
	{
		log.info("listando Usuarios");
		List<UsuarioResidente> usuarios = pv.darUsuarios(); 
		Log.info("listando Usuarios :" + usuarios.size() + "usuarios existentes" );
		
		return usuarios; 
	}
	
	
	public List<VOUsuarioResidente> darVOUsuarios ()
	{
        log.info ("Generando los VO de los usuarios");
         List<VOUsuarioResidente> voUsuarios = new LinkedList<VOUsuarioResidente> ();
        for (UsuarioResidente usuario : pv.darUsuarios())
        {
        	voUsuarios.add(usuario);
        }
        log.info ("Generando los VO de los usuarios: " + voUsuarios.size() + " usuarios existentes");
       return voUsuarios;
	}
	
	
	
	
	public List<UsuarioResidente> consultarVacunados(Timestamp fechaI, Timestamp fechaF, String ordenamiento)
	{
		log.info("listando Usuarios vacunados RFC10");
		List<UsuarioResidente> usuarios = pv.consultarVacunados(fechaI, fechaF, ordenamiento);
		return usuarios; 
	}
	
	
	public List<UsuarioResidente> consultarNoVacunados(Timestamp fechaI, Timestamp fechaF, String ordenamiento)
	{
		log.info("listando Usuarios vacunados RFC11");
		List<UsuarioResidente> usuarios = pv.consultarNoVacunados(fechaI, fechaF, ordenamiento);
		return usuarios; 
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar los Procesos de Vacunacion
	 *****************************************************************/
	
	public ProcesoVacunacion adicionarProcesoVacunacion(int numDosis, int dosisAplicadas, int vacunado, int concentimiento, long idUsuario)
	{
        log.info ("Adicionando Procesos de Vacunacion");
        ProcesoVacunacion procesoVacunacion = pv.adicionarProcesoVacunacion(numDosis, dosisAplicadas, vacunado, concentimiento, idUsuario); 		
        log.info ("Adicionando Procesos de Vacunacion: " + procesoVacunacion.getId());
        return procesoVacunacion;
	}
	
	
	public Long actualizarProcesoVacunacion(long id, int numDosis, int dosisAplicadas, int vacunado, int concentimiento)
	{
        log.info ("actualizando Procesos de Vacunacion");
        Long procesoVacunacionActualizado = pv.actualizarProcesoVacunacion(id, numDosis, dosisAplicadas, vacunado,concentimiento); 
        log.info ("Se ha modificado el proceso de vacunacion con id" + id);
        return procesoVacunacionActualizado; 
	}
	
	
	public ProcesoVacunacion darProcesoVacunaPorIDUsuario(long idUsuario)
	{
        log.info ("dando proceso de usuario");
        ProcesoVacunacion proceso = pv.darProcesoVacunaPorIDUsuario(idUsuario); 
        log.info ("se ha encontrado el proceso del usuario suministrado");
        return proceso; 
	}
	
	
	public double mostrarIndiceVacunacion()
	{
		log.info ("mostrando indice");
		double indice = pv.mostrarIndiceVacunacion(); 
		log.info ("indice calculado");
		return indice; 
		
	}
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las Citas
	 *****************************************************************/
	
	public Cita adicionarCita(Timestamp fechaHora, String estado, long idEPS, long idPuntoVacunacion, long idUsuario)
	{
        log.info ("Adicionando cita");
        Cita cita = pv.adicionarCita(fechaHora, estado, idEPS, idPuntoVacunacion, idUsuario); 	
        log.info ("Adicionando cita: " + cita.getId() +" al usuario: "+idUsuario);
        return cita;
	}
	
	
	public Cita darCitaPorID(long idCita)
	{
        log.info ("dando Cita");
        Cita cita = pv.darCitaPorID(idCita); 
        log.info ("se ha suministrado la informacion de la Cita buscada");
        return cita; 
	}
	
	
	public List<Cita> darCitasPorRangoFechaYPunto(Timestamp fechaI, Timestamp fechaF, long idPunto)
	{
		log.info("listando citas por usuario y rango de fechas");
		List<Cita> citas = pv.darCitasPorRangoFechaYPunto(fechaI, fechaF, idPunto); 
		Log.info("listando citas :" + citas.size() + "citas existentes" );
		
		return citas; 
	}
	
	
	public List<BigDecimal> darIDCitasPorUsuario(long idUsuario)
	{

		List<BigDecimal> citas = pv.darIDCitasPorUsuario(idUsuario); 
		return citas; 
	}
	
	
	public Long actualizarEstadoCita(long id, long idUsuario, String estado)
	{
        log.info ("actualizando Cita");
        Long citaActualizada = pv.actualizarCita(id, idUsuario, estado);  
        log.info ("Se ha actualizado la cita" + id + "para el usuario: " + idUsuario + "con el estado: " + estado);
        return citaActualizada; 
	}
	
	
	public int numCitasEnFechaHora(Timestamp fechaI, Timestamp fechaF, long idPunto)
	{
		log.info ("calculando numero de citas en la hora dada en el punto dado");
		int resp =  pv.numCitasEnFechaHora(fechaI, fechaF, idPunto); 
		log.info ("numero encontrado de forma exitosa");
		return resp; 
	}
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las Vacunas
	 *****************************************************************/
	
	public Vacuna adicionarVacuna(String nombre, String informacion, int numDosis, String condiciones, int cantidad)
	{
        log.info ("Adicionando Vacuna");
        Vacuna vacuna = pv.adicionarVacuna(nombre, informacion, numDosis, condiciones, cantidad); 	
        log.info ("Adicionando Vacuna: " + vacuna.getId());
        return vacuna;
	}
	
	
	public Vacuna darVacunaPorID(long idVacuna)
	{
		Vacuna vacuna = pv.darVacunaPorID(idVacuna);   
		return vacuna; 
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las dosis de las vacunas
	 *****************************************************************/
	public DosisVacuna adicionarDosisVacuna(int aplicada, long idUsuario, long idPuntoVacunacion, long idVacuna)
	{
        log.info ("Adicionando Dosis de Vacuna");
        
        
        DosisVacuna dosis = pv.adicionarDosisVacuna(aplicada, idUsuario, idPuntoVacunacion, idVacuna); 
        log.info ("Adicionando dosis de vacuna: " + dosis.getId() + "a la vacuna con id : " + dosis.getIdVacuna());
        return dosis;
	}
	
	
	public Long actualizarDosisVacunaAplicada(long idDosis, long idUsuario)
	{
        log.info ("actualizando dosis aplicada");
        Long dosisAplicadaActualizada = pv.actualizarDosisVacunaAplicada(idDosis, idUsuario);  
        log.info ("Se ha actualizado la dosis con ID: "  + idDosis);
        return dosisAplicadaActualizada; 
	}
	
	
	public Long modificarPuntoDeVacunacionDosisPorID( long idPuntoVacunacion, long idVacuna)
	{
        Long dosisActualizada = pv.modificarPuntoDeVacunacionDosisPorID(idPuntoVacunacion, idVacuna);  
        return dosisActualizada; 
	}
	
	
	public DosisVacuna darDosisVacunaPorID(long idDosis)
	{
        log.info ("dando dosis de la vacuna");
        DosisVacuna dosis = pv.darDosisVacunaPorID(idDosis); 
        log.info ("se ha suministrado la informacion de la dosis buscada");
        return dosis; 
	}
	
	
	public DosisVacuna darDosisVacunaPorIdUsuario(long idUsuario)
	{
        log.info ("dando dosis de la vacuna");
        DosisVacuna dosis = pv.darDosisVacunaPorIDUsuario(idUsuario);  
        log.info ("se ha suministrado la informacion de la dosis buscada");
        return dosis; 
	}
	
	
	public List<BigDecimal> darDosisPorVacuna(long idVacuna)
	{

		List<BigDecimal> dosis = pv.darDosisPorVacuna(idVacuna); 
		return dosis; 
	}
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las Entradas del Plan
	 *****************************************************************/
	public EntradaPlan adicionarEntradaPlan(int fase, int etapa, Timestamp fechaInicio, Timestamp fechaFin, String condiciones, long idMinisterio)
	{
        log.info ("Adicionando EntradaPlan");
        EntradaPlan entradaPlan = pv.adicionarEntradaPlan(fase, etapa, fechaInicio, fechaFin, condiciones, idMinisterio); 	
        log.info ("Adicionando EntradaPlan: " + entradaPlan.getId() +" al ministerio: "+idMinisterio);
        return entradaPlan;
	}
	
	
	/* ****************************************************************
	 * 			Métodos para manejar la relacion TieneVacuna
	 *****************************************************************/
	
	public TieneVacuna adicionarTieneVacuna(long idVacuna, long idEPS)
	{
        log.info ("Adicionando TieneVacuna");
        TieneVacuna tieneVacuna = pv.adicionarTieneVacuna(idVacuna, idEPS); 	
        log.info ("Adicionando TieneVacuna: eps" + idEPS +" a la vacuna: "+idVacuna);
        return tieneVacuna;
	}

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 11 números que indican el número de tuplas borradas en las tablas 
	 */
	public long [] limpiarVacuAndes()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = pv.limpiarVacuAndes();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}
	

	
	
	
	
}
