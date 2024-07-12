package uniandes.isis2304.parranderos.interfazDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Cita;
import uniandes.isis2304.parranderos.negocio.DosisVacuna;
import uniandes.isis2304.parranderos.negocio.EPSRegional;
import uniandes.isis2304.parranderos.negocio.ProcesoVacunacion;
import uniandes.isis2304.parranderos.negocio.PuntoVacunacion;
import uniandes.isis2304.parranderos.negocio.UsuarioResidente;
import uniandes.isis2304.parranderos.negocio.VOCita;
import uniandes.isis2304.parranderos.negocio.VODosisVacuna;
import uniandes.isis2304.parranderos.negocio.VOEPSRegional;
import uniandes.isis2304.parranderos.negocio.VOEntradaPlan;
import uniandes.isis2304.parranderos.negocio.VOMinisterioSalud;
import uniandes.isis2304.parranderos.negocio.VOProcesoVacunacion;
import uniandes.isis2304.parranderos.negocio.VOPuntoVacunacion;
import uniandes.isis2304.parranderos.negocio.VOTieneVacuna;
import uniandes.isis2304.parranderos.negocio.VOUsuarioResidente;
import uniandes.isis2304.parranderos.negocio.VOVacuna;
import uniandes.isis2304.parranderos.negocio.VacuAndes;
import uniandes.isis2304.parranderos.negocio.Vacuna;

public class InterfazVacuAndes extends JFrame implements ActionListener 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazVacuAndes.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigVacuAndes.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_VacuAndes.json"; 
	
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * Asociación a la clase principal del negocio.
     */
    private VacuAndes vacuAndes;
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;
    
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazVacuAndes( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        vacuAndes = new VacuAndes(tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout(new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos para la configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar( menuBar );	
    }
    
    
	/* ****************************************************************
	 * 			CRUD de Ministerio de Salud
	 *****************************************************************/
    
    public void adicionarMinisterioSalud( )
    {
    	try 
    	{
    		String priorizacionVacuna = JOptionPane.showInputDialog (this, "parametros priorizacion de la Vacuna?", "Adicionar Ministerio de Salud", JOptionPane.QUESTION_MESSAGE);
    		if (priorizacionVacuna != null)
    		{
    			VOMinisterioSalud tb = vacuAndes.adicionarMinisterioSalud(priorizacionVacuna); 
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear el ministerio de salud; NULL");
        		}
        		String resultado = "En MinisterioSalud \n\n";
        		resultado += "MinisterioSalud adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    
    public void listarMinisterios( )
    {
    	try 
    	{
			List <VOMinisterioSalud> lista = vacuAndes.darVOMinisterioSalud(); 

			String resultado = "En listarMinisterios";
			resultado +=  "\n" + listarMinisterios(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
	/* ****************************************************************
	 * 			CRUD de EPSRegional
	 *****************************************************************/
    public void adicionarEPSRegional( )
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "nombre de la eps?", "agregar nombre", JOptionPane.QUESTION_MESSAGE);
    		int telefono = Integer.parseInt(JOptionPane.showInputDialog(this, "telefono de la eps?", "agregar telefono", JOptionPane.QUESTION_MESSAGE));
    		String region = JOptionPane.showInputDialog (this, "region de la EPS?", "agregar region", JOptionPane.QUESTION_MESSAGE);
    		long idMinisterio = Long.parseLong(JOptionPane.showInputDialog (this, "identificador del ministerio asociado?", "agregar identifcador asociado", JOptionPane.QUESTION_MESSAGE));
    		int capacidadVacunas = Integer.parseInt(JOptionPane.showInputDialog(this, "capacidad total de dosis que se puedene almacenar?", "agregar telefono", JOptionPane.QUESTION_MESSAGE));
    		
    		if (nombre != null && telefono > 0 && region != null && idMinisterio > 0)
    		{
    			VOEPSRegional tb = vacuAndes.adicionarEPSRegional(nombre, telefono, region, idMinisterio, capacidadVacunas); 
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear la EPSRegional; NULL");
        		}
        		String resultado = "La EPSRegional \n\n";
        		resultado += "EPSRegional adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
	/* ****************************************************************
	 * 			CRUD de Punto Vacunacion
	 *****************************************************************/
    
    public void adicionarPuntoVacunacion( )
    {
    	try 
    	{
    		String localizacion = JOptionPane.showInputDialog (this, "localizacion del punto?", "agregar la localizacion", JOptionPane.QUESTION_MESSAGE);
    		int capacidadSimultanea = Integer.parseInt(JOptionPane.showInputDialog (this, "capacidad simultanea?", "agregar capacidad simultanea", JOptionPane.QUESTION_MESSAGE));
    		int capacidadTotalDiaria = Integer.parseInt(JOptionPane.showInputDialog (this, "capacidad total diaria?", "agregar capacidad total diaria", JOptionPane.QUESTION_MESSAGE));
    		String infraestructura = JOptionPane.showInputDialog (this, "infraestructura?", "infraetsructura", JOptionPane.QUESTION_MESSAGE);
    		String tipo = JOptionPane.showInputDialog (this, "tipo? tipos posibles: HOSPITALES, CLINICAS, CENTRO_SALUD, ESTADIOS, PARQUEADERO_CENTROCOMERCIAL, OTRO", "agregar tipo", JOptionPane.QUESTION_MESSAGE);
    		long idEPS = Long.parseLong(JOptionPane.showInputDialog (this, "id de la eps asociada?", "agregar id de la eps asociada", JOptionPane.QUESTION_MESSAGE));
    		String estado = JOptionPane.showInputDialog (this, "estado del punto de vacunacion? Opciones: DISPONIBLE, NO_DISPONIBLE", "estado", JOptionPane.QUESTION_MESSAGE);
    		long capacidadTotal = Long.parseLong(JOptionPane.showInputDialog (this, "capacidad total de ususarios que se pueden registrar?", "capacidadTotal", JOptionPane.QUESTION_MESSAGE));
    		long capacidadDosis = Long.parseLong(JOptionPane.showInputDialog (this, "capacidad máxima de dosis que se pueden almacenar?", "capacidadDosis", JOptionPane.QUESTION_MESSAGE));
    		
    		
    		if (capacidadTotal > 0 && capacidadDosis > 0 && localizacion != null && capacidadSimultanea > 0 && capacidadTotalDiaria > 0 && infraestructura != null && tipo != null && idEPS > 0 && estado != null)
    		{
    			VOPuntoVacunacion tb = vacuAndes.adicionarPuntoVacuncaion(localizacion, capacidadSimultanea, capacidadTotalDiaria, infraestructura, tipo, idEPS, estado, capacidadTotal, capacidadDosis); 
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear el PuntoVacuncaion; NULL");
        		}
        		String resultado = "Punto de vacunacion \n\n";
        		resultado += "Punto de Vacunacion adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void actualizarEstadoPuntoVacunacion( )
    {
    	try 
    	{
    		long idPunto = Long.parseLong(JOptionPane.showInputDialog (this, "Punto de vacunacion a actualizar", "idPunto nombre", JOptionPane.QUESTION_MESSAGE));
    		String estado = JOptionPane.showInputDialog (this, "nuevo estado del punto de vacunacion?", "agregar numero dosis", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		if (idPunto > 0 && estado != null && !(estado.equals("")) && (estado.equals("DISPONIBLE") || estado.equals("NO_DISPONIBLE") ) ) 
    		{
    			Long resp = vacuAndes.actualizarEstadoPuntoVacunacion(idPunto, estado); 
        		if (resp == -1)
        		{
        			throw new Exception ("No se pudo actualizar el proceso de vacunacion; NULL");
        		}
        		String resultado = "El proceso de vacunacion \n\n";
        		resultado += "Punto de Vacunacion actualizado exitosamente: " + resp + "nuevo estado para el punto:" +idPunto+ "es : " + estado;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    public void deshabilitarPuntoVacunacion( )
    {
    	try 
    	{
    		long idPunto = Long.parseLong(JOptionPane.showInputDialog (this, "Punto de vacunacion a actualizar", "idPunto nombre", JOptionPane.QUESTION_MESSAGE));
    		String estado = "NO_DISPONIBLE"; 
    		
    		
    		if (idPunto > 0) 
    		{
    			Long resp = vacuAndes.actualizarEstadoPuntoVacunacion(idPunto, estado);
    			Long resp2 = vacuAndes.actualizarPuntoVacunacionUsuarios(idPunto, 1); 
        		if (resp == -1)
        		{
        			throw new Exception ("No se pudo actualizar el proceso de vacunacion; NULL");
        		}
        		String resultado = "El proceso de vacunacion \n\n";
        		resultado += "Punto de Vacunacion actualizado exitosamente: " + resp + "nuevo estado para el punto:" +idPunto+ "es : " + estado;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void rehabilitarPuntoVacunacion( )
    {
    	try 
    	{
    		long idPunto = Long.parseLong(JOptionPane.showInputDialog (this, "Punto de vacunacion a actualizar", "idPunto nombre", JOptionPane.QUESTION_MESSAGE));
    		String estado = "DISPONIBLE"; 
    		
    		
    		if (idPunto > 0) 
    		{
    			Long resp = vacuAndes.actualizarEstadoPuntoVacunacion(idPunto, estado);
    			System.out.println("el estado se actualizo exitosamente:" + resp);
        		if (resp == -1)
        		{
        			throw new Exception ("No se pudo actualizar el proceso de vacunacion; NULL");
        		}
        		String resultado = "El proceso de vacunacion \n\n";
        		resultado += "Punto de Vacunacion actualizado exitosamente: " + resp + "nuevo estado para el punto:" +idPunto+ "es : " + estado;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    			
    			PuntoVacunacion puntoVac = vacuAndes.darPuntoVacunacionPorID(idPunto); 
    			
    			int cantidadActualUsuarios = vacuAndes.cantidadActualPersonasPuntoVacunacion(idPunto);
    			System.out.println(cantidadActualUsuarios);
    			long capacidadTotal = puntoVac.getCapacidadTotal(); 
    			System.out.println(capacidadTotal);
    			long cupo = capacidadTotal - cantidadActualUsuarios;
    			System.out.println(cupo);
    			
    			List<BigDecimal> listaLimbo = vacuAndes.darIdUsuariosEnPuntoVac(1);
    			
    			System.out.println(listaLimbo.size());
    			for(BigDecimal a: listaLimbo)
    			{
    				System.out.println("id del usuario en limbo: " + a.longValue()  );
    			}
    			
    			if(cupo > 0 && listaLimbo.size() > 0)
    			{
    				System.out.println("entra al if");
    				int i = 0; 
    				long iteraciones = Math.min(cupo, listaLimbo.size()); 
    				
    				System.out.println("iteraciones = " + iteraciones);
    				
    				while(i < iteraciones) 
    				{
    					System.out.println("entra al while");
    					
    					long idUsuario = listaLimbo.get(i).longValue();
    					
    					System.out.println(idUsuario);
    					
    					vacuAndes.actualizarPuntoVacunacionUsuarioPorId(idPunto, idUsuario); 
    					System.out.println("se actualizo el usuario " + idUsuario + "al punto " + idPunto );
    					
    					i++; 
    					System.out.println(i);
    				}
    				
    			}
    			
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    //RFC1
    public void darCiudadanosAtendisoPorPunto()
    {
    	long id = Long.parseLong(JOptionPane.showInputDialog (this, "id del punto?", "agregar id del punto", JOptionPane.QUESTION_MESSAGE));
    	String fechaI = JOptionPane.showInputDialog (this, "fecha y hora de Inicio? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	String fechaF = JOptionPane.showInputDialog (this, "fecha y hora Final? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	
    	if((fechaI != null) && (fechaF != null))
    	{
    		Timestamp fechaInicio = Timestamp.valueOf(fechaI);
    		Timestamp fechaFin = Timestamp.valueOf(fechaF);
    		
    		List<UsuarioResidente> ciudadanos  = vacuAndes.darCiudadanosAtendisoPorPunto(id, fechaInicio, fechaFin); 
    		String resp = listarCiudadanosAtendisoPorPunto(ciudadanos);
    		
    		panelDatos.actualizarInterfaz(resp);
    	}
    }
    
    
    //RFC2
    
    public void dar20PuntosMasEfectivos()
    {
    	String fechaI = JOptionPane.showInputDialog (this, "fecha y hora de Inicio? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	String fechaF = JOptionPane.showInputDialog (this, "fecha y hora Final? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	
    	if((fechaI != null) && (fechaF != null))
    	{

    		
    		Timestamp fechaInicio = Timestamp.valueOf(fechaI);
    		Timestamp fechaFin = Timestamp.valueOf(fechaF);
    		
    		String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(fechaInicio);
    		String d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(fechaFin);
    		
    		System.out.println("fehca Inicio: " + s);
    		System.out.println("fehca Inicio: " + d);
    		
    		System.out.println("timestamp i:" + fechaInicio);
    		System.out.println("timestamp f:" + fechaFin);
    		
    		List<Object []> puntos = vacuAndes.dar20PuntosMasEfectivos(fechaInicio, fechaFin); 
    		listar20PuntosMasEfectivos(puntos); 
    	}
    	
    }
    
    //RFC7
    
    public void analizarOperacionVacuAndes()
    {
    	long idUsuario = Long.parseLong(JOptionPane.showInputDialog (this, "id del usuario a revisar?", "idUsuario", JOptionPane.QUESTION_MESSAGE));
    	String fecha = JOptionPane.showInputDialog (this, "fecha y hora? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	
    	if(idUsuario > 0 && fecha != null && !(fecha.equals("")))
    	{
    		
    		Timestamp fechaFinal = Timestamp.valueOf(fecha);
    		
    		 Calendar cal = Calendar.getInstance();
    	     cal.setTime(fechaFinal);
    	     cal.add(Calendar.DATE, -10); 	   
    	     Timestamp fechaInicial = new Timestamp(cal.getTimeInMillis());
    	     System.out.println(fechaInicial);
    	     System.out.println("fecha de entrada es " + fechaFinal);
    	   
    	}
    	
    }
    
    //RFC8
    
    public void analizarCohorteCiudadana()
    {
    	long idUsuario = Long.parseLong(JOptionPane.showInputDialog (this, "id del usuario a revisar?", "idUsuario", JOptionPane.QUESTION_MESSAGE));
    	String fecha = JOptionPane.showInputDialog (this, "fecha y hora? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	
    	if(idUsuario > 0 && fecha != null && !(fecha.equals("")))
    	{
    		
    		Timestamp fechaFinal = Timestamp.valueOf(fecha);
    		
    		 Calendar cal = Calendar.getInstance();
    	     cal.setTime(fechaFinal);
    	     cal.add(Calendar.DATE, -10); 	   
    	     Timestamp fechaInicial = new Timestamp(cal.getTimeInMillis());
    	     System.out.println(fechaInicial);
    	     System.out.println("fecha de entrada es " + fechaFinal);
    	   
    	}
    	
    }
    
    //RFC9
    
    public void encontrarCiudadanosEnContacto()
    {
    	long idUsuario = Long.parseLong(JOptionPane.showInputDialog (this, "id del usuario a revisar?", "idUsuario", JOptionPane.QUESTION_MESSAGE));
    	String fecha = JOptionPane.showInputDialog (this, "fecha y hora? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	
    	if(idUsuario > 0 && fecha != null && !(fecha.equals("")))
    	{
    		
    		//se obtiene la fecha inicialy final de busqueda
    		Timestamp fechaFinal = Timestamp.valueOf(fecha);
    		
    		 Calendar cal = Calendar.getInstance();
    	     cal.setTime(fechaFinal);
    	     cal.add(Calendar.DATE, -10);
    	     
    	     Timestamp fechaInicial = new Timestamp(cal.getTimeInMillis());
    	     
    	     
//    	     System.out.println(fechaInicial);
//    	     System.out.println("fecha de entrada es " + fechaFinal);
    	     
    	     
    	     List<BigDecimal> idCitasUsuario = vacuAndes.darIDCitasPorUsuario(idUsuario); 
    	     List<Timestamp> fechas = new ArrayList<>(); 
    	     
    	     PuntoVacunacion puntoVac; 
    	     long idDelPunto = -1; 
    	     
    	     for(BigDecimal a : idCitasUsuario)
    	     {
    	    	 Cita citaActual = vacuAndes.darCitaPorID(a.longValue()); 
    	    	 Timestamp fechaActualCita = citaActual.getFechaHora(); 
    	    	 puntoVac = vacuAndes.darPuntoVacunacionPorID(citaActual.getIdPuntoVacunacion()); 
    	    	 idDelPunto = puntoVac.getId(); 
    	    	 fechas.add(fechaActualCita); 
    	     }
    	     
    	     for(Timestamp a : fechas)
    	     {
    	    	 Timestamp Tiempo20MinAntes = new Timestamp(a.getTime() - TimeUnit.MINUTES.toMillis(20));
    	    	 Timestamp Tiempo20MinDespues = new Timestamp(a.getTime() + TimeUnit.MINUTES.toMillis(20));
    	    	 List<Cita> c = vacuAndes.darCitasPorRangoFechaYPunto(Tiempo20MinAntes, Tiempo20MinDespues, idDelPunto); 
    	    	 
    	    	 for(Cita w : c)
    	    	 {
    	    		System.out.println("el usuario " + idUsuario + "estuvo en contacto con: " + w.getIdUsuario());  
    	    	 }
    	     }
    	     
    	     
    	     
    	   
    	}
    	
    }
    
    
    
	/* ****************************************************************
	 * 			CRUD de UsuarioResidente
	 *****************************************************************/
    
    public void adicionarUsuarioResidente( )
    {
    	
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "nombre del usuario?", "agregar nombre", JOptionPane.QUESTION_MESSAGE);
    		int edad = Integer.parseInt(JOptionPane.showInputDialog (this, "edad del usuario?", "agregar edad", JOptionPane.QUESTION_MESSAGE));
    		int telefono = Integer.parseInt(JOptionPane.showInputDialog (this, "telefono del usuario?", "agregar telefono", JOptionPane.QUESTION_MESSAGE));
    		String condicionesMedicas = JOptionPane.showInputDialog (this, "condicionesMedicas del usuario?", "agregar condicionesMedicas", JOptionPane.QUESTION_MESSAGE);
    		String profesion = JOptionPane.showInputDialog (this, "profesion del usuario?", "agregar profesion", JOptionPane.QUESTION_MESSAGE);
    		int fase = Integer.parseInt(JOptionPane.showInputDialog (this, "fase en la que se encuentra el usuario?", "agregar fase", JOptionPane.QUESTION_MESSAGE));
    		int etapa = Integer.parseInt(JOptionPane.showInputDialog (this, "etapa en la que se encuentra?", "agregar etapa", JOptionPane.QUESTION_MESSAGE));
    		int prioridad = Integer.parseInt(JOptionPane.showInputDialog (this, "prioridad del usuario?", "agregar prioridad", JOptionPane.QUESTION_MESSAGE));
    		long idEPS = Long.parseLong(JOptionPane.showInputDialog (this, "id de la eps asociada?", "agregar idEps", JOptionPane.QUESTION_MESSAGE));
    		String idPuntoVacunacionP = JOptionPane.showInputDialog (this, "id del punto de vacunacion asociado?", "agregar idPuntoVacunacion", JOptionPane.QUESTION_MESSAGE);
    		String StringidTrabaja = JOptionPane.showInputDialog (this, "id del lugar donde trabaja el usuario?", "agregar idPuntoVacunacion", JOptionPane.QUESTION_MESSAGE);
    		
    		long idTrabaja; 
    		long idPuntoVacunacion; 
    		
    		if(nombre != null && edad > 0 && telefono > 0 && condicionesMedicas != null && profesion != null && fase > 0 && etapa > 0 && prioridad > 0 && idEPS > 0)
    		{
    			 
    			
    			if((StringidTrabaja.equals("")) || (StringidTrabaja == null))
    			{
    				idTrabaja = -1; 
    				
    			}else
    			{
    				idTrabaja = Long.parseLong(StringidTrabaja); 
    			}
    			
    			
    			if((idPuntoVacunacionP.equals("")) || (idPuntoVacunacionP == null))
    			{
    				idPuntoVacunacion = 1; 
    				
    			}else
    			{
    				idPuntoVacunacion = Long.parseLong(idPuntoVacunacionP); 
    			}
    			
    			
    			PuntoVacunacion puntoVac = vacuAndes.darPuntoVacunacionPorID(idPuntoVacunacion);
    			String estadoPunto = puntoVac.getEstado(); 
    			
    			if(estadoPunto.endsWith("NO_DISPONIBLE"))
    			{
    				throw new Exception("El punto de vacunacion especificado no se encuentra disponible");
    			}
    			
    			PuntoVacunacion puntoARegistrar = vacuAndes.darPuntoVacunacionPorID(idPuntoVacunacion); 
    			
    			System.out.println(puntoARegistrar);
    			
    			long capacidadTotalPunto = puntoARegistrar.getCapacidadTotal();
    			
    			System.out.println(capacidadTotalPunto);
    			
    			int capacidadActualPunto = vacuAndes.cantidadActualPersonasPuntoVacunacion(idPuntoVacunacion); 
    			
    			System.out.println(capacidadActualPunto);
    			
    			if(capacidadActualPunto + 1 > capacidadTotalPunto)
    			{
    				throw new Exception ("No se pudo crear el UsuarioResidente el cupo esta lleno \n el numero de usuarios registrados es: " + capacidadActualPunto);
    			}else
    			{
    				
        			VOUsuarioResidente tb = vacuAndes.adicionarUsuarioResidente(nombre, edad, telefono, condicionesMedicas, profesion, fase, etapa, prioridad, idEPS, idPuntoVacunacion, idTrabaja);
            		if (tb == null)
            		{
            			throw new Exception ("No se pudo crear el UsuarioResidente; NULL");
            		}
            		String resultado = "UsuarioResidente \n\n";
            		resultado += "UsuarioResidente adicionado exitosamente: " + tb;
        			resultado += "\n Operación terminada";
        			panelDatos.actualizarInterfaz(resultado);
    				
    			}

    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    //dar todos los usuarios de la aplicacion 
    public void listarUsuarios( )
    {
    	try 
    	{
			List <VOUsuarioResidente> lista = vacuAndes.darVOUsuarios(); 

			String resultado = "En listarUsuarios";
			resultado +=  "\n" + listarUsuarios(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    //RFC10
    
    public void consultarVacunados()
    {
    	String fechaIStr = JOptionPane.showInputDialog (this, "fecha y hora? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	String fechaFStr = JOptionPane.showInputDialog (this, "fecha y hora? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	String condiciones = JOptionPane.showInputDialog (this, "condiciones de ordenamiento?", "parametros disponibles: ID, NOMBRE , EDAD ,TELEFONO ,CONDICIONESMEDICAS ,PROFESION ,FASE ,ETAPA ,PRIORIDAD ,IDEPS ,IDPUNTOVACUNACION ,IDTRABAJA", JOptionPane.QUESTION_MESSAGE);
    	
    	
    	try 
    	{
    		Timestamp fechaI = Timestamp.valueOf(fechaIStr);
    		Timestamp fechaF = Timestamp.valueOf(fechaFStr);
    		
    		
			List <UsuarioResidente> lista = vacuAndes.consultarVacunados(fechaI, fechaF, condiciones); 
			
			
			String resultado = "Usuarios vacunados \n";
			resultado += " en el rango de fechas: " + fechaI + " hasta " + fechaF +  "  son: \n"; 
			
			for(UsuarioResidente u : lista)
			{
			 System.out.println(u);
			 resultado += " \n " + u.toString(); 	
			}
			
			resultado += "\n operacion terminada"; 
			
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    //RFC11
    
    public void consultarNoVacunados()
    {
    	String fechaIStr = JOptionPane.showInputDialog (this, "fecha y hora? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	String fechaFStr = JOptionPane.showInputDialog (this, "fecha y hora? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    	String condiciones = JOptionPane.showInputDialog (this, "condiciones de ordenamiento?", "parametros disponibles: ID, NOMBRE , EDAD ,TELEFONO ,CONDICIONESMEDICAS ,PROFESION ,FASE ,ETAPA ,PRIORIDAD ,IDEPS ,IDPUNTOVACUNACION ,IDTRABAJA", JOptionPane.QUESTION_MESSAGE);
    	
    	
    	try 
    	{
    		Timestamp fechaI = Timestamp.valueOf(fechaIStr);
    		Timestamp fechaF = Timestamp.valueOf(fechaFStr);
    		
    		
			List <UsuarioResidente> lista = vacuAndes.consultarNoVacunados(fechaI, fechaF, condiciones); 
			
			
			String resultado = "Usuarios NO vacunados \n";
			resultado += " en el rango de fechas: " + fechaI + " hasta " + fechaF +  "  son: \n"; 
			
			for(UsuarioResidente u : lista)
			{
			 System.out.println(u);
			 resultado += " \n " + u.toString(); 	
			}
			
			resultado += "\n operacion terminada"; 
			
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    
   //RFC12 
    
   public void consultarFuncionamiento()
   {
	   int year = Integer.parseInt(JOptionPane.showInputDialog (this, "año en el cual se quiere realizar la consulta", "año", JOptionPane.QUESTION_MESSAGE));
	   
	   
	   String resp = ""; 
	   
	   //manejo de fechas
	   
	   Calendar calendar = Calendar.getInstance();
	   calendar.set(Calendar.YEAR, year);
	   
	   int numberOfWeeks = calendar.getWeeksInWeekYear();
	   
	   for(int i = 1; i <= numberOfWeeks; i++)
	   {

		   calendar.set(Calendar.WEEK_OF_YEAR, i);
	        Date yourDate2 = calendar.getTime();

	        calendar.setTime(yourDate2);

	        Date start2,end2;

	        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	        start2 = calendar.getTime();

	        calendar.add(Calendar.DATE, 6);
	        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	        end2 = calendar.getTime();

	        
	        Timestamp fechaI = new Timestamp(start2.getTime()); 
	        Timestamp fechaF = new Timestamp(end2.getTime());
	        
	        List<Object[]> puntosMasEfectivos = vacuAndes.dar20PuntosMasEfectivos(fechaI, fechaF);
	        List<Object[]> puntosMenosEfectivos = vacuAndes.dar20PuntosMasEfectivos(fechaI, fechaF);
	        
	        resp += "\n \n Semana " + i ;
	        resp += "\n"+ start2 +" - "+ end2 + "\n";
	        
	        
	        resp += "20 puntos con mayor afluencia\n";
	        if(puntosMasEfectivos.size() > 0 )
	        {
	        	for(Object[] obj: puntosMasEfectivos)
	        	{
	        		resp += "   afluencia: " + obj[1]  + "---- Punto: " + obj[0].toString() + "\n";
	        	}
	        	
	        }else
	        {
	        	resp += "   No hay puntos para este rango de fechas";
	        }
	        
	        resp += "\n 20 puntos con menor afluencia \n";
	        
	        if(puntosMenosEfectivos.size() > 0)
	        {
	        	for(Object[] obj: puntosMenosEfectivos)
	        	{
	        		resp += "   afluencia: " + obj[1]  + "---- Punto: " + obj[0].toString() + "\n";
	        	}
	        }else {
	        	resp += "   No hay puntos para este rango de fechas";
	        }

	        
	   }
	   
       
       resp += "\n fin de la operacion"; 
       panelDatos.actualizarInterfaz(resp);
	   
	     
	  
   }
   
   //RFC13
   
   public void consultarLideres()
   {
//	   List<Object[]> fechas = darIntervaloFechas(10); 
//	   int i = 1; 
//	   
//	   System.out.println("tamaño fechas: " + fechas.size());
//	   
//	   for(Object[] obj : fechas)
//	   {
//		   System.out.println("pareja" + i);
//		   System.out.println(obj[0] + " ---- " + obj[1] + " \n");
//		   i++;
//	   }
//	   
//	   System.out.println("Fin de la operacion");
	   
	   String resp = ""; 
	   
	   
	   //1 EPS
	   resp += "Las EPS que logran aplicar al menos el 90% de las dosis \n";
	   
	   resp += EPS90Porcientoaplicacion(3);
	   resp += EPS90Porcientoaplicacion(5);
	   resp += EPS90Porcientoaplicacion(10);
	   
	   
	   //2.

	   panelDatos.actualizarInterfaz(resp);
   }
   
   //1EPS 
   
   public String EPS90Porcientoaplicacion(int intervalo)
   {
	   String resp = ""; 
	   
	   resp += "\n Intervalo de " + intervalo + " dias: \n"; 
	   
	   List<Object[]> fechas3 = darIntervaloFechas(intervalo);
	   
	   System.out.println(fechas3.size());
	   
	   int i = 1;
	   
	   for(Object[] obj: fechas3)
	   {   
		   
		   resp += " intervalo"  + i + "\n";
		   
		   Date fechaIDate = (Date)obj[0];
		   Date fechaFDate = (Date)obj[1];
		   
		   Timestamp fechaI = new Timestamp(fechaIDate.getTime()); 
		   Timestamp fechaF = new Timestamp(fechaFDate.getTime()); 
		   
		   List<Object[]> vacunasEPS = vacuAndes.darVacunasSuminstradasPorEPS(fechaI, fechaF);
		   
		   for(Object[] tuplas: vacunasEPS)
		   {
			   long idEPS = (Long)tuplas[0];
			   long cantidadVac = (Long)tuplas[1]; 
			   
			   EPSRegional eps =vacuAndes.darEPSRegionalPorID(idEPS);
			   long numVac = eps.getCapacidadVacunas(); 
			   
			   double porcentajeAplicacion = (double)((cantidadVac/numVac)*100.00);
			   
			   if(porcentajeAplicacion >= 0.9)
			   {
				   resp += "  EPS: " + idEPS + "vacunas aplicadas: " + cantidadVac; 
			   }
		   }
		   
		   i++; 
	   }
	   
	   return resp; 
   }
   
   //2. Punto vac alto desempeño
   
   
   /**
    * da intervalos de dias para el año actual
    * @param numDias tamaño del intervalo
    * @return lista con parejas de intervalos
    */
   public List<Object[]> darIntervaloFechas(int numDias)
   {
	   
	   List<Object[]> fechas = new ArrayList<Object[]>(); 

       Calendar cal = Calendar.getInstance();
       cal.set(Calendar.DAY_OF_YEAR, 1);    
       Date inicio = cal.getTime();

       cal.set(Calendar.MONTH, 11); 
       cal.set(Calendar.DAY_OF_MONTH, 31);
       Date fin = cal.getTime(); 
       
       
       Date inicioIntervalo, finIntervalo ;
       
       GregorianCalendar gcal = new GregorianCalendar();
      
       gcal.setTime(inicio);

       while (gcal.getTime().before(fin))
       {
    	   
    	   Object[] pareja = new Object[2]; 
    	   
    	   inicioIntervalo = gcal.getTime(); 
    	   gcal.add(Calendar.DAY_OF_YEAR, numDias);
    	   finIntervalo = gcal.getTime(); 
    	   
    	   pareja[0] = inicioIntervalo; 
    	   pareja[1] = finIntervalo; 
    	   
    	   fechas.add(pareja); 
       }
       
       return fechas; 
   }
    
    
    
	/* ****************************************************************
	 * 			CRUD de ProcesoVacunacion
	 *****************************************************************/
    public void adicionarProcesoVacunacion( )
    {
    	try 
    	{
    		long idUsuario = Long.parseLong(JOptionPane.showInputDialog (this, "id del usuario asociado al proceso", "agregar nombre", JOptionPane.QUESTION_MESSAGE));
    		int numDosis = Integer.parseInt(JOptionPane.showInputDialog (this, "numero de dosis a dar?", "agregar numero dosis", JOptionPane.QUESTION_MESSAGE));
    		int dosisAplicadas = Integer.parseInt(JOptionPane.showInputDialog (this, "dosis aplicadas al momento?", "agregar dosis aplicadas", JOptionPane.QUESTION_MESSAGE));
    		int vacunado = Integer.parseInt(JOptionPane.showInputDialog (this, "el usuario esta vacunado? (0 = false, 1 = true)", "agregar estado vacunado", JOptionPane.QUESTION_MESSAGE));
    		int concentimiento = Integer.parseInt(JOptionPane.showInputDialog (this, "el usuario da concentimiento? (0 = false, 1 = true)", "agregar nombre", JOptionPane.QUESTION_MESSAGE));
    		
    		
    		if (numDosis >= 1 && numDosis <= 2 && dosisAplicadas >= 0 && dosisAplicadas <= 2 && (vacunado == 0 || vacunado == 1) && (concentimiento == 0 || concentimiento == 1) && idUsuario > 0 ) 
    		{
    			VOProcesoVacunacion tb = vacuAndes.adicionarProcesoVacunacion(numDosis, dosisAplicadas, vacunado, concentimiento, idUsuario); 
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear la EPSRegional; NULL");
        		}
        		String resultado = "El proceso de vacunacion \n\n";
        		resultado += "ProcesoVacunacion adicionado exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    //actualiza el proceso de vacunacion
    public void actualizarProcesoVacunacion( )
    {
    	try 
    	{
    		long idProceso = Long.parseLong(JOptionPane.showInputDialog (this, "id del proceso a actualizar", "agregar nombre", JOptionPane.QUESTION_MESSAGE));
    		int numDosis = Integer.parseInt(JOptionPane.showInputDialog (this, "numero de dosis a dar?", "agregar numero dosis", JOptionPane.QUESTION_MESSAGE));
    		int dosisAplicadas = Integer.parseInt(JOptionPane.showInputDialog (this, "dosis aplicadas al momento?", "agregar dosis aplicadas", JOptionPane.QUESTION_MESSAGE));
    		int vacunado = Integer.parseInt(JOptionPane.showInputDialog (this, "el usuario esta vacunado? (0 = false, 1 = true)", "agregar estado vacunado", JOptionPane.QUESTION_MESSAGE));
    		int concentimiento = Integer.parseInt(JOptionPane.showInputDialog (this, "el usuario da concentimiento? (0 = false, 1 = true)", "agregar nombre", JOptionPane.QUESTION_MESSAGE));
    		
    		
    		if (numDosis >= 1 && numDosis <= 2 && dosisAplicadas >= 0 && dosisAplicadas <= 2 && (vacunado == 0 || vacunado == 1) && (concentimiento == 0 || concentimiento == 1) && idProceso > 0 ) 
    		{
    			Long resp = vacuAndes.actualizarProcesoVacunacion(idProceso, numDosis, dosisAplicadas, vacunado, concentimiento);
        		if (resp == -1)
        		{
        			throw new Exception ("No se pudo actualizar el proceso de vacunacion; NULL");
        		}
        		String resultado = "El proceso de vacunacion \n\n";
        		resultado += "ProcesoVacunacion actualizado exitosamente: " + resp;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    //FC3
    
    public void mostrarIndiceVacunacion()
    {
    	double indice = vacuAndes.mostrarIndiceVacunacion(); 
    	panelDatos.actualizarInterfaz("El indice es: " + indice + "%");
    }

    
    
	/* ****************************************************************
	 * 			CRUD de Cita
	 *****************************************************************/
    
    public void adicionarCita( )
    {
    	try 
    	{
    		
    		long idUsuario = Long.parseLong(JOptionPane.showInputDialog (this, "id del usuario?", "idUsuario", JOptionPane.QUESTION_MESSAGE));
    		long idEPS = Long.parseLong(JOptionPane.showInputDialog (this, "id de la eps?", "idEPS", JOptionPane.QUESTION_MESSAGE));
    		long idPuntoVacunacion = Long.parseLong(JOptionPane.showInputDialog (this, "id del putno de vacunacion?", "idPuntoVacunacion", JOptionPane.QUESTION_MESSAGE));
    		String fechaHora = JOptionPane.showInputDialog (this, "fecha y hora de la cita? formato:yyyy-mm-dd hh:mm:ss[.fffffffff]", "fecha", JOptionPane.QUESTION_MESSAGE);
    		String estado = JOptionPane.showInputDialog (this, "estado de la cita? opciones: FINALIZADA, CANCELADA, ESPERA", "estado", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		if (fechaHora != null && estado != null && idEPS > 0 && idPuntoVacunacion > 0 && idUsuario > 0)
    		{
    			
    			PuntoVacunacion puntoVac = vacuAndes.darPuntoVacunacionPorID(idPuntoVacunacion); 
    			String estadoPuntoVac = puntoVac.getEstado(); 
    			
    			if(estadoPuntoVac.equals("NO_DISPONIBLE"))
    			{
    				throw new Exception(" El punto de vacunacion especificado no se encuentra disponible");
    			}
    			
    			Timestamp fechaHoraTimestamp = Timestamp.valueOf(fechaHora);
    			
    			//manejo de fechas
    			Date fecha20MinMasD = new Date(fechaHoraTimestamp.getTime() + TimeUnit.MINUTES.toMillis(20)); 
    			Date fecha20MinMenosD = new Date(fechaHoraTimestamp.getTime() - TimeUnit.MINUTES.toMillis(20));
    			
    			Timestamp fecha20MinMenos = new Timestamp(fecha20MinMenosD.getTime());
    			Timestamp fecha20MinMas = new Timestamp(fecha20MinMasD.getTime());
    			
    			long w = fechaHoraTimestamp.getTime(); 
    			Calendar cal = Calendar.getInstance();
    			cal.setTimeInMillis(w);
    			
    			int year = cal.get(Calendar.YEAR);
    			int day = cal.get(Calendar.DAY_OF_MONTH);
    			int month = cal.get(Calendar.MONTH);
    			
    			Timestamp diaInicio = Timestamp.valueOf(year+"-"+month+"-"+day+" "+"0:0:0");
    			Timestamp diaFin = Timestamp.valueOf(year+"-"+month+"-"+day+" "+"23:59:59");
    			
    			System.out.println(fecha20MinMenos);
    			System.out.println(fecha20MinMas);
    			System.out.println(diaInicio);
    			System.out.println(diaFin);
    			
    			//fin manejo de fechas
    			
    			PuntoVacunacion puntoActual = vacuAndes.darPuntoVacunacionPorID(idPuntoVacunacion); 
    			
    			long capacidadSimultanea = puntoActual.getCapacidadSimultanea(); 
    			long capacidadTotalDiaria = puntoActual.getCapacidadTotalDiaria(); 
    			
    			int citasEnElDia = vacuAndes.numCitasEnFechaHora(diaInicio, diaFin, idPuntoVacunacion);
    			int citasEn20Min = vacuAndes.numCitasEnFechaHora(fecha20MinMenos, fecha20MinMas, idPuntoVacunacion); 
    			
    			if((citasEn20Min + 1 <= capacidadSimultanea) && (citasEnElDia + 1 <= capacidadTotalDiaria))
    			{     			
        			VOCita tb = vacuAndes.adicionarCita(fechaHoraTimestamp, estado, idEPS, idPuntoVacunacion, idUsuario);  
            		if (tb == null)
            		{
            			throw new Exception ("No se pudo crear la cita; NULL");
            		}
            		String resultado = "En Cita \n\n";
            		resultado += "Cita adicionada exitosamente: " + tb;
        			resultado += "\n Operación terminada";
        			panelDatos.actualizarInterfaz(resultado);
    				
    			}else {
    				throw new Exception("la capacidad ya se ha alcanzado"); 
    			}

    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    
    //actualiza la cita
    public void actualizarEstadoCita( )
    {
    	try 
    	{
    		long idCita = Long.parseLong(JOptionPane.showInputDialog (this, "id de la cita?", "idUsuario", JOptionPane.QUESTION_MESSAGE));
    		long idUsuario = Long.parseLong(JOptionPane.showInputDialog (this, "id del usuario?", "idUsuario", JOptionPane.QUESTION_MESSAGE));
    		String estado = JOptionPane.showInputDialog (this, "estado de la cita? opciones: FINALIZADA, CANCELADA, ESPERA", "estado", JOptionPane.QUESTION_MESSAGE);
    		
    		
    		if (idCita > 0 && idUsuario > 0 && estado != null) 
    		{
    			
    			if(estado.equals("FINALIZADA"))
    			{
    				
    				Cita citaActual = vacuAndes.darCitaPorID(idCita); 
    				
    				PuntoVacunacion puntoVac =  vacuAndes.darPuntoVacunacionPorID(citaActual.getIdPuntoVacunacion());
    				String estadoPrevio = puntoVac.getEstado(); 
    				
    				if(estadoPrevio.equals("NO_DISPONIBLE"))
    				{
    					throw new Exception("El punto de vacunacion no se encuentra disponible"); 
    				}
    				
    				
    				long idDosisVacuna = Long.parseLong(JOptionPane.showInputDialog (this, "id de la dosis aplicada?", "idDosisVacuna", JOptionPane.QUESTION_MESSAGE));

    				//actualizacion del proceso de vacunacion de la persona con la cita
    				ProcesoVacunacion procesoUsuario = vacuAndes.darProcesoVacunaPorIDUsuario(idUsuario); 
    				
    				long id = procesoUsuario.getId();
    				int numDosis = procesoUsuario.getNumDosis(); 
    				int dosisAplicadas = procesoUsuario.getDosisAplicadas(); 
    				int vacunado = procesoUsuario.getVacunado(); 
    				int concentimiento = procesoUsuario.getConcentimiento(); 
    				
    				if(numDosis == 2 && dosisAplicadas == 1)
    				{

    					DosisVacuna dosisVieja = vacuAndes.darDosisVacunaPorIdUsuario(idUsuario);

    					long idVacunaDosisVieja = dosisVieja.getIdVacuna(); 

    					DosisVacuna dosisNueva = vacuAndes.darDosisVacunaPorID(idDosisVacuna); 

    					long idVacunaDosisNueva = dosisNueva.getIdVacuna(); 
    					
    					//revisa que las dosis sean de la misma tecnologia
    					if(idVacunaDosisNueva != idVacunaDosisVieja)
    					{
    						throw new Exception("las dosis deben ser de la misma tecnologia"); 
    					}else {
    						
    						dosisAplicadas++; 
    						vacunado = 1; 
  
    						vacuAndes.actualizarProcesoVacunacion(id, numDosis, dosisAplicadas, vacunado, concentimiento);
    	        			Long resp = vacuAndes.actualizarEstadoCita(idCita, idUsuario, estado);
    	            		if (resp == -1)
    	            		{
    	            			throw new Exception ("No se pudo actualizar la cita; NULL");
    	            		}
    	            		String resultado = "Cita \n\n";
    	            		resultado += "Cita actualizada exitosamente: " + resp;
    	        			resultado += "\n Operación terminada";
    	        			panelDatos.actualizarInterfaz(resultado);
    	        			
    	    				//se actualiza la dosis aplicada: puntoVacunacion = null; aplicada = 1
    	    				vacuAndes.actualizarDosisVacunaAplicada(idDosisVacuna, idUsuario); 
    					}
    					
    				}else if(numDosis == 2 && dosisAplicadas == 0)
    				{
    					
    					dosisAplicadas++; 

						vacuAndes.actualizarProcesoVacunacion(id, numDosis, dosisAplicadas, vacunado, concentimiento);
	        			Long resp = vacuAndes.actualizarEstadoCita(idCita, idUsuario, estado);
	            		if (resp == -1)
	            		{
	            			throw new Exception ("No se pudo actualizar la cita; NULL");
	            		}
	            		String resultado = "Cita \n\n";
	            		resultado += "Cita actualizada exitosamente: " + resp;
	        			resultado += "\n Operación terminada";
	        			panelDatos.actualizarInterfaz(resultado);
	        			
	    				//se actualiza la dosis aplicada: puntoVacunacion = null; aplicada = 1
	    				vacuAndes.actualizarDosisVacunaAplicada(idDosisVacuna, idUsuario); 
	        			
    				}else if(numDosis == 1 && dosisAplicadas == 0)
    				{
    					
    					dosisAplicadas++;
    					vacunado = 1; 

						vacuAndes.actualizarProcesoVacunacion(id, numDosis, dosisAplicadas, vacunado, concentimiento);
	        			Long resp = vacuAndes.actualizarEstadoCita(idCita, idUsuario, estado);
	            		if (resp == -1)
	            		{
	            			throw new Exception ("No se pudo actualizar la cita; NULL");
	            		}
	            		String resultado = "Cita \n\n";
	            		resultado += "Cita actualizada exitosamente: " + resp;
	        			resultado += "\n Operación terminada";
	        			panelDatos.actualizarInterfaz(resultado);
    					
	    				//se actualiza la dosis aplicada: puntoVacunacion = null; aplicada = 1
	    				vacuAndes.actualizarDosisVacunaAplicada(idDosisVacuna, idUsuario); 
    				}
    				
    				
    				
    			}else {
    				
        			Long resp = vacuAndes.actualizarEstadoCita(idCita, idUsuario, estado);
            		if (resp == -1)
            		{
            			throw new Exception ("No se pudo actualizar la cita; NULL");
            		}
            		String resultado = "Cita \n\n";
            		resultado += "Cita actualizada exitosamente: " + resp;
        			resultado += "\n Operación terminada";
        			panelDatos.actualizarInterfaz(resultado);
    				
    			}

    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
	/* ****************************************************************
	 * 			Métodos para manejar las Vacunas
	 *****************************************************************/
    
    public void adicionarVacuna()
    {
    	try 
    	{
    		String nombre = JOptionPane.showInputDialog (this, "nombre de la vacuna?", "nombre", JOptionPane.QUESTION_MESSAGE);
    		String informacion = JOptionPane.showInputDialog (this, "informacion de la vacuna?", "informacion", JOptionPane.QUESTION_MESSAGE);
    		int numDosis = Integer.parseInt(JOptionPane.showInputDialog(this, "numero de dosis a aplocar", "numDosis", JOptionPane.QUESTION_MESSAGE));
    		String condiciones = JOptionPane.showInputDialog (this, "condiciones para almacenamiento?", "condiciones", JOptionPane.QUESTION_MESSAGE);
    		int cantidad = Integer.parseInt(JOptionPane.showInputDialog(this, "cantidad de dosis disponibles", "cantidad", JOptionPane.QUESTION_MESSAGE));
    		
    		
    		if (nombre != null && informacion != null && (numDosis == 1 || numDosis == 2) && condiciones != null && cantidad >= 0)
    		{
    			VOVacuna tb = vacuAndes.adicionarVacuna(nombre, informacion, numDosis, condiciones, cantidad); 
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear la vacuna; NULL");
        		}else {
        			Long idVacuna = tb.getId(); 
        			
        			for(int i = 0; i < cantidad; i++)
        			{
        				 vacuAndes.adicionarDosisVacuna(0,-1,-1,idVacuna);
        				 
        			}
        			
        		}
        		String resultado = "En Vacuna \n\n";
        		resultado += "Vacuna adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    
	/* ****************************************************************
	 * 			Métodos para manejar las dosis de vacunas
	 *****************************************************************/
    
    public void adicionarDosisVacuna()
    {
    	try 
    	{
    		String idUsuarioP = JOptionPane.showInputDialog (this, "usuario asignado a la dosis?", "idUsuario", JOptionPane.QUESTION_MESSAGE);
    		String idPuntoVacunacionP = JOptionPane.showInputDialog (this, "punto de vacunacion en el cual se encuentra la dosis?", "idPuntoVacunacion", JOptionPane.QUESTION_MESSAGE);
    		long idVacuna = Long.parseLong(JOptionPane.showInputDialog (this, "vacuna a la cual pertence la dosis?", "idVacuna", JOptionPane.QUESTION_MESSAGE));
    		
    		
    		if (idVacuna >= 0)
    		{
    			
    			long idUsuario;
    			long idPuntoVacunacion; 
    			
    			if(idUsuarioP.equals("")|| idUsuarioP == null)
    			{
    				idUsuario = -1; 
    			}else {
    				idUsuario = Long.parseLong(idUsuarioP); 
    			}
    			
    			
    			if(idPuntoVacunacionP.equals("")|| idPuntoVacunacionP == null)
    			{
    				idPuntoVacunacion = -1; 
    			}else {
    				idPuntoVacunacion = Long.parseLong(idPuntoVacunacionP); 
    			}
    			
    
    			
    			VODosisVacuna tb = vacuAndes.adicionarDosisVacuna(0, idUsuario, idPuntoVacunacion, idVacuna); 
    			
    			
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear la dosis de la vacuna; NULL");
        		}
        		String resultado = "En DosisVacuna \n\n";
        		resultado += "DosisVacuna adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    
    public void modificarPuntoDeVacunacionDosisPorLote( )
    {
    	try 
    	{
    		long idPuntoVacunacion = Long.parseLong(JOptionPane.showInputDialog (this, "Punto de vacunacion al cual llega el lote(Vacuna)?", "idPuntoVacunacion", JOptionPane.QUESTION_MESSAGE));
    		long idVacuna = Long.parseLong(JOptionPane.showInputDialog (this, "lote(Vacuna) que llega?", "idVacuna", JOptionPane.QUESTION_MESSAGE));
    		
    		
    		if (idPuntoVacunacion > 0 && idVacuna > 0) 
    		{
    			
    			Vacuna vacunaActual = vacuAndes.darVacunaPorID(idVacuna);
    			PuntoVacunacion puntoActual = vacuAndes.darPuntoVacunacionPorID(idPuntoVacunacion); 
    			
    			int vacunasARegistrar = vacunaActual.getCantidad(); 
    			long capacidadMaxima = puntoActual.getCapacidadDosis(); 
    			int cantidadActualDosis = vacuAndes.cantidadActualvacunasPuntoVacunacion(idPuntoVacunacion); 
    			
    			if(vacunasARegistrar + cantidadActualDosis > capacidadMaxima)
    			{
    				throw new Exception ("el numero de dosis a registrar supera la capacidad del punto de vacunacion");
    			}
    			
    			List<BigDecimal> dosisParaActualizar = vacuAndes.darDosisPorVacuna(idVacuna); 
    			System.out.println(dosisParaActualizar.size());
    			
    			for(BigDecimal a : dosisParaActualizar)
    			{
    				long idDosis = a.longValue(); 
    				System.out.println("el id es: " + idDosis);
    				Long resp = vacuAndes.modificarPuntoDeVacunacionDosisPorID(idPuntoVacunacion, idDosis); 
            		if (resp == -1)
            		{
            			throw new Exception ("No se pudo modificar la dosis "+idDosis+" ; NULL");
            		}
    			}


    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    
	/* ****************************************************************
	 * 			Métodos para manejar las Entradas del Plan
	 *****************************************************************/
    
    public void adicionarEntradaPlan( )
    {
    	try 
    	{
    		
    		int fase = Integer.parseInt(JOptionPane.showInputDialog(this, "fase de vacunacion", "fase", JOptionPane.QUESTION_MESSAGE));
    		int etapa = Integer.parseInt(JOptionPane.showInputDialog(this, "etapa de vacunacion", "etapa", JOptionPane.QUESTION_MESSAGE));
    		String fechaHoraInicio = JOptionPane.showInputDialog (this, "fecha y hora de inicio? formato:yyyy-mm-dd hh:mm:ss", "fechaHoraInicio", JOptionPane.QUESTION_MESSAGE);
    		String fechaHoraFin = JOptionPane.showInputDialog (this, "fecha y hora final? formato:yyyy-mm-dd hh:mm:ss", "fechaHoraFin", JOptionPane.QUESTION_MESSAGE);
    		String condiciones = JOptionPane.showInputDialog (this, "condiciones de vacunacion de la entrada", "condiciones", JOptionPane.QUESTION_MESSAGE);
    		long idMinisterio = Long.parseLong(JOptionPane.showInputDialog (this, "id del ministerio asociado?", "idEPS", JOptionPane.QUESTION_MESSAGE));
    		
    		
    		
    		if (fase > 0 && etapa > 0 && fechaHoraInicio != null && !(fechaHoraInicio.equals("")) && fechaHoraFin != null && !(fechaHoraFin.equals("")) && idMinisterio >= 0 && condiciones != null)
    		{
    			Timestamp fechaInicio = Timestamp.valueOf(fechaHoraInicio);
    			Timestamp fechaFin = Timestamp.valueOf(fechaHoraFin);
    			
    			VOEntradaPlan tb = vacuAndes.adicionarEntradaPlan(fase, etapa, fechaInicio, fechaFin, condiciones, idMinisterio); 
        		if (tb == null)
        		{
        			throw new Exception ("No se pudo crear la entrada del plan; NULL");
        		}
        		String resultado = "En EntradaPlan \n\n";
        		resultado += "EntradaPlan adicionada exitosamente: " + tb;
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    /* ****************************************************************
	 * 			Métodos para manejar la relacion TieneVacuna
	 *****************************************************************/
    
    
    public void adicionarTieneVacuna()
    {
    	try 
    	{
    		long idVacuna = Long.parseLong(JOptionPane.showInputDialog (this, "id del lote(Vacuna) a asociar?", "idVacuna", JOptionPane.QUESTION_MESSAGE));
    		long idEPS = Long.parseLong(JOptionPane.showInputDialog (this, "id de la eps a asociar el lote(Vacuna)?", "idEPS", JOptionPane.QUESTION_MESSAGE));
    		
    		if (idVacuna >= 0 && idEPS >= 0)
    		{
    			 
    			EPSRegional epsActual = vacuAndes.darEPSRegionalPorID(idEPS);
    			Vacuna vacunaActual = vacuAndes.darVacunaPorID(idVacuna); 
    			
    			int maximoVacunaEPS = epsActual.getCapacidadVacunas(); 

    			int vacunasActualesEPS = vacuAndes.cantidadActualvacunasEPS(idEPS);

    			System.out.println(vacunasActualesEPS);
    			int totalVacunas = vacunaActual.getCantidad(); 
    			
    			if(vacunasActualesEPS + totalVacunas > maximoVacunaEPS)
    			{
    				throw new Exception ("La cantidad de vacunas sobrepasa el limite de la EPS");
    			}else
    			{
        			
        			VOTieneVacuna tb = vacuAndes.adicionarTieneVacuna(idVacuna, idEPS);
            		if (tb == null)
            		{
            			throw new Exception ("No se pudo crear la relacion Tiene Vacuna; NULL");
            		}
            		String resultado = "En Tiene Vacuna \n\n";
            		resultado += "Tiene Vacuna adicionada exitosamente: " + tb;
        			resultado += "\n Operación terminada";
        			panelDatos.actualizarInterfaz(resultado);
    				
    			}

    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
    		// Ejecución de la demo y recolección de los resultados
			long eliminados [] = vacuAndes.limpiarVacuAndes();
			
			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " MinisterioSalud eliminados\n";
			resultado += eliminados [1] + " EntradaPlan eliminados\n";
			resultado += eliminados [2] + " EPSRegional eliminados\n";
			resultado += eliminados [3] + " Vacuna eliminadas\n";
			resultado += eliminados [4] + " PuntoVacunacion eliminados\n";
			resultado += eliminados [5] + " TieneVacuna eliminados\n";
			resultado += eliminados [6] + " UsuarioResidente eliminados\n";
			resultado += eliminados [7] + " ProcesoVacunacion eliminados\n";
			resultado += eliminados [8] + " Rol eliminados\n";
			resultado += eliminados [9] + " Cita eliminados\n";
			resultado += eliminados [10] + " DosisVacuna eliminados\n";
			resultado += "\nLimpieza terminada";
   
			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
    /**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Parranderos Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Germán Bravo\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jiménez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);
    }
    
    
    
    
    

	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/

    
    private String listarUsuarios (List<VOUsuarioResidente> lista) 
    {
    	String resp = "Los Usuarios son:\n";
    	int i = 1;
        for (VOUsuarioResidente usuario : lista)
        {
        	resp += i++ + ". " + usuario.toString() + "\n";
        }
        return resp;
	}
    
    
    
    private String listarMinisterios (List<VOMinisterioSalud> lista) 
    {
    	String resp = "Los Ministerios son:\n";
    	int i = 1;
        for (VOMinisterioSalud ministerios : lista)
        {
        	resp += i++ + ". " + ministerios.toString() + "\n";
        }
        return resp;
	}
    
    
    private String listarCiudadanosAtendisoPorPunto(List<UsuarioResidente> lista) 
    {
    	String resp = "Los usuarios atendidos son:\n";
    	int i = 1;
        for (UsuarioResidente tupla : lista)
        {
        	VOUsuarioResidente usuario = (VOUsuarioResidente) tupla;
        	resp += i + ". usuario: " + usuario + "\n"; 
        	i++; 
        }
        return resp;
	}
    
    
    private void listar20PuntosMasEfectivos(List<Object[]> lista) 
    {
    	String resp = "Los 20 puntos mas efectivos son:\n\n";
    	int i = 1;
    	
        for (Object[] tupla : lista)
        {
        	VOPuntoVacunacion puntoVacunacion = (VOPuntoVacunacion) tupla[0]; 
        	int numVacunas = (int) tupla[1]; 
        	resp += i + ". Punto de vacunacion: " + puntoVacunacion + "  totalVacunas : "+numVacunas+" \n"; 
        	i++; 
        }
        
        panelDatos.actualizarInterfaz(resp);
	}
    
    
    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazVacuAndes.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazVacuAndes interfaz = new InterfazVacuAndes( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
	
	

}
