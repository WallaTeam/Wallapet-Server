/*
 * Nombre: ActualizarAnuncio.java
 * Version: 0.1
 * Autor: Luis Pellicer.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para modificar anuncios.
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

import persistencia.Anuncio;
import persistencia.AnuncioPersistencia;
import persistencia.Cuenta;
import persistencia.CuentaPersistencia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Borrar
 */
@WebServlet("/borrarUsuario.do")
public class BorrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * El GET no debe hacer nada.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA GET PARA BORRAR UN USUARIO");
		response.setStatus(500);
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		PrintWriter out = response.getWriter();

		HttpSession s = request.getSession();
		Cuenta logueado = (Cuenta) s.getAttribute("usuario");
		if(logueado==null){
			out.println("Usuario no logueado");
			response.setStatus(405);
			return;
		}

		String emailCuenta = request.getParameter("mail");
		String emailLogueado = logueado.getEmail();

		try{

			if(emailCuenta.equalsIgnoreCase(emailLogueado)){
				//Se procede al borrado
				CuentaPersistencia cp = new CuentaPersistencia();
				cp.borrarCuenta(emailCuenta);
				out.println("OK - Cuenta borrada");
				response.setStatus(200);

			}else{

				out.println("Permisos insuficientes");
				response.setStatus(403);
			}
		}
		catch(Exception ex){
			//Ha habido una excepcion. Server error.
			ex.printStackTrace();
			out.println("SERVER ERROR");
			response.setStatus(500);
		}


	}
}
