/*
 * Nombre: CrearAnuncio.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para crear anuncios.
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
import persistencia.Anuncio;
import persistencia.AnuncioPersistencia;
/**
 * Servlet implementation class CrearEvento
 */
@WebServlet("/crearAnuncio.do")
public class CrearAnuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * El GET no debe hacer nada.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA POST PARA INTRODUCIR UN ANUNCIO");
		response.setStatus(500);
	}

	/**
	 * CrearAnuncio funciona con POST.
	 * POST /crearAnuncio.do.
	 * Ver documentacion
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		//He recibido un anuncio en formato JSON
		String anuncioJson = request.getParameter("anuncio");
		try{
			Anuncio received = Anuncio.fromJson(anuncioJson);
			AnuncioPersistencia persistencia = new AnuncioPersistencia();
			
			//Creamos el anuncio
			persistencia.createAnuncio(received);
			
			//Respondemos
			out.println("OK ANUNCIO CREADO");
			response.setStatus(200);	
		}catch(Exception ex){
			//Ha habido una excepción
			ex.printStackTrace();
			out.println("SERVER ERROR");
			response.setStatus(500); //Server error
		}	
	}
}
