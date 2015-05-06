/*
 * Nombre: RegistrarUsuario.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para registrar un usuario.
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
package servicios;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistencia.Cuenta;
import persistencia.CuentaPersistencia;
import persistencia.RespuestaRegistro;
import persistencia.Utiles;

/**
 * Servlet implementation class RegistrarUsuario
 */
@WebServlet("/registrarUsuario.do")
public class RegistrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * El GET no debe hacer nada.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA POST PARA REGISTRAR UN USUARIO");
		response.setStatus(500);
	}
	
	/**
	 * Pre: RegistrarUsuario funciona con POST.
	 *      POST /RegistrarUsuario.do?usuario=" 'contenido Json"
	 *      Ver documentacion para mas detalle.
	 * Post: registra un usuario o devuelve error.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		//He recibido un anuncio en formato JSON
		String anuncioJson = request.getParameter("usuario");
		try{
			Cuenta received = Cuenta.fromJson(anuncioJson);
			CuentaPersistencia persistencia = new CuentaPersistencia();
			
			//Guardaremos la contrasena como hash
			received.setContrasegna(Utiles.generateHash(received.getContrasegna()));

			if(persistencia.existsCuenta(received.getEmail(),received.getDNI(),received.getUsuario())){
				RespuestaRegistro rr = new RespuestaRegistro();
				rr.setRespuestaRegistro("mail_o_DNI_o_nick_repetido");

				out.println(RespuestaRegistro.toJson(rr));
			}
			else{
				//Creamos la cuenta
				persistencia.createCuenta(received);
				RespuestaRegistro rr = new RespuestaRegistro();
				rr.setRespuestaRegistro("OK");

				out.println(RespuestaRegistro.toJson(rr));
			}

			response.setStatus(200);		
		}catch(Exception ex){
			
			//Ha habido una excepcion
			ex.printStackTrace();
			out.println("SERVER ERROR");
			
			//Server error
			response.setStatus(500); 
		}	
	}
}
