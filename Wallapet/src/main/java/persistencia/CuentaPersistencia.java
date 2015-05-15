/*
 * Nombre: AnuncioPersistencia.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa la comunicacion 
 *              con la base de datos de la aplicacion.
 * Copyright (C) 2015 Hyena Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package persistencia;

import java.sql.*;


/*
 * Esta clase implementa métodos para acceder a la base de datos de MySql de miniIMDB.
 */
public class CuentaPersistencia {

	// Variables para conectarse a la base de datos
	private String db_driver;
	private String db_username;
	private String db_password;
	
	// Datos del servidor
	private final String DRIVER = "jdbc:mysql://wallapet.com:3306/wallapet";
	private final String USERNAME = "piraces";
	private final String PASSWORD = "";
	
	/**
	 * Pre: Cierto
	 * Post: Construye con los atributos definidos un objeto que permite 
	 *       acceder a la bd de la aplicacion.
	 */
	public CuentaPersistencia() {
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
	 * Pre: email!=null.
	 * Borra una cuenta y todos los anuncios que ha creado dicha cuenta.
	 * En caso de error lanza una excepcion.
	 */
	public void borrarCuenta(String email) throws SQLException{
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		Statement stmt = connection.createStatement();

		// Conversion de formato booleano a numerico
		String sql_1 = "DELETE FROM anuncio" +
				"		WHERE email = '" + email + "'";
		String sql_2 = "DELETE FROM cuenta " +
				"WHERE email = '" + email + "'";

		stmt.executeUpdate(sql_1);
		stmt.executeUpdate(sql_2);
		// Cerramos conexion
		stmt.close();
		connection.close();
	}
	/**
	 * Pre: email!=null y DNI !=null
	 * Post: Devuelve cierto si y solo si existe una cuenta con el email indicado
	 * o con el DNI incidado.
	 * En caso contrario devuelve "false".
	 */
	public boolean existsCuenta(String email, String DNI, String usuario)
			throws SQLException{
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		Statement stmt = connection.createStatement();
		// Ejecutamos consulta.
		ResultSet rs = stmt
				.executeQuery("SELECT * " +
						"FROM cuenta " +
						"WHERE email=\"" + email
						+ "\" OR DNI=\"" + DNI +
						"\" OR usuario=\"" + usuario + "\"");

		boolean exists = rs.next();
		stmt.close();
		connection.close();
		return exists;

	}


	/**
	 * Pre: cuenta != null
	 * Post: Inserta en la base de datos una cuenta nueva. En caso de no poder
	 *       crearla lanza una excepcion SQL.
	 */
	public void createCuenta(Cuenta cuenta) throws SQLException{
		
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		Statement stmt = connection.createStatement();
		
		// Conversion de formato booleano a numerico
		
		stmt.executeUpdate("INSERT INTO cuenta (usuario,DNI,nombre,apellidos,direccion,email,telefono,contrasegna)" +
		  " VALUES ('" + cuenta.getUsuario() + "','" + cuenta.getDNI() + "','" + cuenta.getNombre() + "','" + cuenta.getApellido() +
		  "','" + cuenta.getDireccion() + "','" + cuenta.getEmail() + "'," + cuenta.getTelefono() + ",'" + cuenta.getContrasegna() + "')");

		// Cerramos conexion
		stmt.close();
		connection.close();
	}

	/**
	 * Si esta bien logueado devuelve objeto Cuenta.
	 * Si no, devuelve null.
	 * @return
	 * @throws SQLException
	 */
	public Cuenta loginCuenta(DatosLogin dl) throws SQLException {

		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		Statement stmt = connection.createStatement();
		// Ejecutamos consulta.
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM cuenta WHERE email=\"" + dl.getMail() + "\"");
		if (rs.next()) {
			String DNI = rs.getString("DNI");
			String apellidos = rs.getString("apellidos");
			String nombre = rs.getString("nombre");
			String direccion = rs.getString("direccion");
			int telefono = rs.getInt("telefono");
			String usuario = rs.getString("usuario");
			String retrievedPass = rs.getString("contrasegna");

			if (dl.getPass().equals(retrievedPass)) {
				Cuenta c = new Cuenta();
				c.setDNI(DNI);
				c.setApellido(apellidos);
				c.setNombre(nombre);
				c.setDireccion(direccion);
				c.setEmail(dl.getMail());
				c.setTelefono(telefono);
				c.setUsuario(usuario);
				c.setContrasegna(retrievedPass);
				return c;
			} else {
				return null;
			}

		} else {
			//No hay usuario con dicho mail
			return null;
		}
	}

		/**
		 * Si esta bien logueado devuelve objeto Cuenta.
		 * Si no, devuelve null.
		 * @return
		 * @throws SQLException
		 */
		public Cuenta obtenerCuenta(int idAnuncio) throws SQLException{

			Connection connection = DriverManager.getConnection(db_driver,
					db_username, db_password);
			Statement stmt = connection.createStatement();
			// Ejecutamos consulta.
			ResultSet rs = stmt
					.executeQuery("SELECT * " +
							"FROM anuncio " +
							"WHERE idAnuncio=\"" + idAnuncio+ "\"");


			if(rs.next()){
				String email = rs.getString("email");
				System.out.println(email);


				if(email != null){
					rs = stmt.executeQuery("SELECT * " +
							"FROM cuenta " +
							"WHERE email=\"" +email+ "\"");
					rs.next();
					System.out.println("pruebasss");
					String DNI = rs.getString("DNI");
					String apellidos = rs.getString("apellidos");
					String nombre = rs.getString("nombre");
					String direccion = rs.getString("direccion");
					int telefono = rs.getInt("telefono");
					String usuario = rs.getString("usuario");

					Cuenta c = new Cuenta();
					c.setDNI(DNI);
					c.setApellido(apellidos);
					c.setNombre(nombre);
					c.setDireccion(direccion);
					c.setEmail(email);
					c.setTelefono(telefono);
					c.setUsuario(usuario);
					return c;


				}
				else{
					stmt.close();
					connection.close();
					return null;
				}

			}
			else{
				//No hay usuario con dicho mail
				stmt.close();
				connection.close();
				return null;
			}



		}




}
