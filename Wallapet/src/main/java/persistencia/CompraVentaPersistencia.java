/**
 * Nombre:  CompraVentaPersistencia.java
 * Version: 1.0
 * Autor:  Sergio Soro
 * Fecha: 5-5-2015
 * Descripcion: Este fichero implementa la comunicacion
 *              con la base de datos de la aplicacion en lo que respecta
 *              a Compra venta de anuncios.
 * Copyright (C) 2015 Hyena Technologies
 */

package persistencia;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompraVentaPersistencia {

    // Variables para conectarse a la base de datos
    private String db_driver;
    private String db_username;
    private String db_password;

    // Datos del servidor
    private final String DRIVER = "jdbc:mysql://wallapet.com:3306/wallapet";
    private final String USERNAME = "piraces";
    private final String PASSWORD = "";

    // Constructor
    public CompraVentaPersistencia() {
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
     * Post: Inserta el IDanuncio, email del comprador y la fecha en la base de datos
     *       de la aplicacion. En caso de errores lanza una excepcion SQL.
     */
    public void crearCompra(int id, String email) throws SQLException {

        // Crear conexion.
        Connection connection = DriverManager.getConnection(db_driver,
                db_username, db_password);
        Statement stmt = connection.createStatement();
        Date date = new Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String text = df.format(date);
        System.out.print(text);



        // Insertar en la base de datos.
        stmt.executeUpdate("INSERT INTO compraVenta"
                + "(idAnuncio, email, fecha)"
                + " VALUES "
                + "('" + id + "','"
                + email + "','"
                + text + "')");

        // Cerramos conexion.
        stmt.close();
        connection.close();

    }


}
