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
	private final String DRIVER = "jdbc:mysql://wallapet.com:3306/*******";
	private final String USERNAME = "*******";
	private final String PASSWORD = "*******";
	
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
	 * Pre: cuenta != null
	 * Post: Inserta en la base de datos una cuenta nueva. En caso de no poder
	 *       crearla lanza una excepcion SQL.
	 */
	public void createCuenta(Cuenta cuenta) throws SQLException{
		
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		Statement stmt = connection.createStatement();
		
		// Conversion de formato booleano a numerico
		
		stmt.executeUpdate("INSERT INTO cuenta (DNI,nombre,apellidos,direccion,email,telefono,contrasegna)" +
		  " VALUES ('" + cuenta.getDNI() + "','" + cuenta.getNombre() + "','" + cuenta.getApellido() +
		  "','" + cuenta.getDireccion() + "','" + cuenta.getEmail() + "'," + cuenta.getTelefono() + ",'" + cuenta.getContrasegna() + "')");

		// Cerramos conexion
		stmt.close();
		connection.close();
	}
}
