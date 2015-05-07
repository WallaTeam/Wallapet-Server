/*
 * Nombre: AnuncioPersistencia.java
 * Version: 0.1
 * Autor: Luis Pellicer.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa la comunicacion 
 *              con la base de datos de la aplicacion en lo que respecta
 *              a Anuncio.
 * Copyright (C) 2015 Hyena Technologies
 */
package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class AnuncioPersistencia {

	// Variables para conectarse a la base de datos
	private String db_driver;
	private String db_username;
	private String db_password;
	
	// Datos del servidor
	private final String DRIVER = "jdbc:mysql://****/wallapet";
	private final String USERNAME = "**";
	private final String PASSWORD = "**";

	// Constructor
	public AnuncioPersistencia() {
		db_driver = DRIVER;
		db_username = USERNAME;
		db_password = PASSWORD;

		if (db_driver.contains("mysql")) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				System.err.println("Mysql driver not found");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Pre: a != null
	 * Post: Inserta el anuncio a en la base de datos de la aplicacion. En caso
	 *       de errores lanza una excepcion SQL.
	 */
	public void createAnuncio(Anuncio a) throws SQLException {

		// Crear conexion.
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		Statement stmt = connection.createStatement();
		
		// Insertar en la base de datos.
		stmt.executeUpdate("INSERT INTO anuncio"
				+ "(email,estado,descripcion,tipoIntercambio,especie,precio," +
				"rutaImagen,titulo)"
				+ " VALUES "
				+ "('" + a.getEmail() + "','"
				+ a.getEstado() + "','"
				+ a.getDescripcion() + "','"
				+ a.getTipoIntercambio() + "','"
				+ a.getEspecie() + "',"
				+ a.getPrecio() + ",'"
				+ a.getRutaImagen() + "','"
				+ a.getTitulo() + "')");

		// Cerramos conexion.
		stmt.close();
		connection.close();

	}
	
	/**
	 * Pre: Cierto
	 * Post: Devuelve el anuncio si existe, null en caso contrario.
	 */
	public Anuncio getAnuncio(int id) throws SQLException {

		// Crear conexion.
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		Statement stmt = connection.createStatement();
		
		// Ejecutamos consulta.
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM anuncio WHERE idAnuncio=" + id
						+ ""); 
		if (rs.next()) {
			
			// En el caso de que exista anuncio con el mismo id.
				
			String email = rs.getString("email");
			String estado = rs.getString("estado");
			String descripcion = rs.getString("descripcion");
			String tipoIntercambio = rs.getString("tipoIntercambio");
			String especie = rs.getString("especie");
			String rutaImagen = rs.getString("rutaImagen");
			String titulo = rs.getString("titulo");
			double precio = rs.getDouble("precio");
			
			// Crear el Anuncio
			Anuncio a = new Anuncio();
			a.setEmail(email);
			a.setEstado(estado);
			a.setDescripcion(descripcion);
			a.setTipoIntercambio(tipoIntercambio);
			a.setEspecie(especie);
			a.setRutaImagen(rutaImagen);
			a.setPrecio(precio);
			a.setTitulo(titulo);
			a.setIdAnuncio(id);

			stmt.close();
			connection.close();

			return a;
		} else {
			
			// No hay anuncio con el mismo id. Se cierra la conexion.
			stmt.close();
			connection.close();
			return null;
		}
	}

	/**
	 * Pre: Cierto
	 * Post: Devuelve una lista de anuncios segun las opciones tipo de anuncio,
	 *       especie o palabras clave, que no estén cerrados.
	 */
	public List<Anuncio> searchAnuncios(String tipoAnuncio, String especie,
			String palabrasClave) throws SQLException {

		// Crear conexion.
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		Statement stmt = connection.createStatement();
		
		// Comprobar opciones.
		String opciones = "";
		if (tipoAnuncio != null && tipoAnuncio != "") {
			opciones += " tipoIntercambio = \"" + tipoAnuncio + "\" AND";
		}
		if (especie != null && especie != "") {
			opciones += " especie = \"" + especie + "\" AND";
		}
		if (palabrasClave != null && palabrasClave != "") {
			
			// Comprobar errores.
			String[] terminos = new String[0];
			try {
				terminos = palabrasClave.split(",");
			} catch (PatternSyntaxException ex) {
				
				// Excepcion.
			}


			// Formar busqueda a partir de las palabras claves.
			for(int i = 0; i< terminos.length; i++){
				opciones += " titulo LIKE \'%" + terminos[i] + "%\' OR" ;
				if( i == terminos.length -1){
					opciones += " descripcion LIKE \'%" + terminos[i] + "%\' AND" ;
				}
				else{
					opciones += " descripcion LIKE \'%" + terminos[i] + "%\' OR" ;
				}
				
			}
		}
		opciones += " estado='Abierto'";
		System.out.println("SELECT * FROM anuncio WHERE"+ opciones);
		
		// Ejecutar consulta.
		ResultSet rs = stmt.executeQuery("SELECT * FROM anuncio WHERE "
				+ opciones);
		
		//  Lista de anuncios que coinciden con las caracteristicas de la busqueda.
		List<Anuncio> anuncios = new ArrayList<Anuncio>();
		while (rs.next()) {
			
			/*
			 *  Leer atributos de los anuncios, crear un nuevo anuncio
			 *   e incluirlo en la lista anuncios.
			 */
			String email = rs.getString("email");
			String estado = rs.getString("estado");
			String descripcion = rs.getString("descripcion");
			String tipoIntercambio = rs.getString("tipoIntercambio");
			String _especie = rs.getString("especie");
			String rutaImagen = rs.getString("rutaImagen");
			double precio = rs.getDouble("precio");
			String titulo = rs.getString("titulo");
			int id = rs.getInt("idAnuncio");
			
			Anuncio a = new Anuncio();
			
			a.setEmail(email);
			a.setEstado(estado);
			a.setDescripcion(descripcion);
			a.setTipoIntercambio(tipoIntercambio);
			a.setEspecie(_especie);
			a.setRutaImagen(rutaImagen);
			a.setPrecio(precio);
			a.setIdAnuncio(id);
			a.setTitulo(titulo);
			
			anuncios.add(a);

		}
		stmt.close();
		connection.close();
		return anuncios;
	}
	
	/**
	 * Pre: Cierto
	 * Post: Actualiza el anuncio de la bd con el id igual a idAnuncio y 
	 *       devuelve true. En caso de que no pueda actualizarse devuelve false.
	 */
	public boolean updateAnuncio(int idAnuncio,String especie,
		String descripcion, String tipoIntercambio,String titulo, double precio,
								 String ruta){

		// Crear conexion
		try {
			// Conexiones con la base de datos.
			Connection connection = DriverManager.getConnection(db_driver,
					db_username, db_password);
		
			Statement stmt = connection.createStatement();
			
			// Restricciones.
			
			// Comprobar si el anuncio existe en la base de datos.
			Anuncio anuncio = getAnuncio(idAnuncio);
			if(anuncio != null){
				stmt.executeUpdate("UPDATE anuncio SET "
						+ "tipoIntercambio="+"'"+tipoIntercambio + "',"
						+ "especie=" + "'" + especie+"'," 
						+ "precio=" + precio + ",titulo=" + "'" + titulo + "',"
						+ "descripcion=" + "'" + descripcion + "',"
						+ "rutaImagen=" + "'" + ruta + "'"
						+ " WHERE idAnuncio=" + idAnuncio + ";");
				
				// En caso de modificacion correcta devuelve true.
				stmt.close();
				connection.close();
				return true;
			}
			
			// En caso de error en la modificacion devuelve false.
			else{
				stmt.close();
				connection.close();
				return false;
			}



			
		} // En caso de error devuelve false. 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Pre:  Cierto
	 * Post: Cierra el anuncio de la bd con id igual a idAnuncio.
	 *       En caso de no poder cerrarlo lanza una excepcion.
	 */
	public void cerrarAnuncio(int idAnuncio) throws SQLException{
		// Conexiones con la base de datos.
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);

		Statement stmt = connection.createStatement();
		String sql = "UPDATE anuncio " +
					 "SET estado = 'Cerrado' " +
					 "WHERE idAnuncio=" +
				idAnuncio;

		stmt.executeUpdate(sql);

		stmt.close();
		connection.close();
	}
	/**
	 * Pre:  Cierto 
	 * Post: Elimina el anuncio de la bd con id igual a idAnuncio y devuelve true.
	 *       En caso de no poder eliminar el anuncio devuelve false.
	 */
	public boolean deleteAnuncio(int idAnuncio){

		// Crear conexion
		try {
			
			// Conexiones con la base de datos.
			Connection connection = DriverManager.getConnection(db_driver,
					db_username, db_password);
		
			Statement stmt = connection.createStatement();
			
			// Restricciones.
			
			// Comprobar si el anuncio existe en la base de datos.
			Anuncio anuncio = getAnuncio(idAnuncio);
			if(anuncio != null){
				int numModificadas=
						stmt.executeUpdate("DELETE FROM anuncio"
								         + " WHERE idAnuncio="+idAnuncio+";");
				
				// En caso de eliminacion correcta devuelve true.
				stmt.close();
				connection.close();
				return numModificadas==1;

			}
			
			// En caso de error en la eliminacion devuelve false.
			else{
				stmt.close();
				connection.close();
				return false;
			}	
		} 
		
		// En caso de error devuelve false. 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
