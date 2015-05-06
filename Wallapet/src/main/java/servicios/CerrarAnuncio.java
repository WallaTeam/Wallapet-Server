/*
 * Nombre: BorrarAnuncio.java
 * Version: 0.1
 * Autor: Luis Pellicer.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para borrar anuncios.
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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class BorrarAnuncio
 */
@WebServlet("/cerrarAnuncio.do")
public class CerrarAnuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * El GET no debe hacer nada.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA POST PARA CERRAR UN ANUNCIO");
		response.setStatus(500);
	}
	
	/**
	 * Pre: BorrarAnuncio funciona con POST.
	 *      POST /BorrarAnuncio.do?anuncio=" 'contenido Json"
	 *      Ver documentacion para mas detalle.
	 * Post: Borra el anuncio o informa del error.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		HttpSession s = request.getSession();
		Cuenta logueado = (Cuenta) s.getAttribute("usuario");
		if(logueado==null){
			out.println("Usuario no logueado");
			response.setStatus(405);
			return;
		}
		try {
			
			// Comprar si la peticion es correcta.
			String idParameter = request.getParameter("id");
			if (idParameter == null || idParameter == "") {
				out.println("ERROR: PARAMETRO ID PASADO DE FORMA INCORRECTA");
				response.setStatus(500);

			} else {
				
				int id = Integer.parseInt(idParameter);
				
				// Obtenemos el anuncio de la base de datos
				AnuncioPersistencia persistencia = new AnuncioPersistencia();

				Anuncio a =persistencia.getAnuncio(id);
				if(a==null) {
					//No existe el anuncio a cerrar
					out.println("No existe el anuncio a cerrar");
					response.setStatus(404);
				}
				else if(logueado.getEmail().equalsIgnoreCase(a.getEmail())){
					//Es su anuncio, puede cerrarlo
					persistencia.cerrarAnuncio(id);
					out.println("OK - Cerrado");
					response.setStatus(200);
				}
				else{
					//Error de permisos
					out.println("Error de permisos");
					response.setStatus(403);

				}

				
			}
		} catch (Exception ex) {
			out.println("ERROR: SERVER ERROR");
			response.setStatus(500);
		}

	}

}
