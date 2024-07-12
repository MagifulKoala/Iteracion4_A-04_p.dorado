package uniandes.isis2304.parranderos.negocio;

public class Rol implements VORol
{
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * identificador del rol
	 */
	private long id;
	
	/**
	 * nombre del rol
	 */
	private String rol;
	
	/**
	 * identificador del usuario asociado al rol
	 */
	private long idUsuario;

	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * constructor por defecto
	 */
	public Rol() 
	{
		this.id = 0;
		this.rol = "";
		this.idUsuario = 0;
	} 
	
	/**
	 * 
	 * @param id del rol
	 * @param rol nombre  del rol
	 * @param idUsuario asociado al rol
	 */
	public Rol(long id, String rol, long idUsuario) 
	{
		this.id = id;
		this.rol = rol;
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @return the idUsuario
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rol [id=" + id + ", rol=" + rol + ", idUsuario=" + idUsuario + "]";
	} 
	
	
	
	
}
