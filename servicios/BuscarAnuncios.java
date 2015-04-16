/*
 * Nombre: BuscarAnuncios.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para buscar anuncios.
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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import persistencia.Anuncio;
import persistencia.AnuncioPersistencia;

/**
 * Servlet implementation class BuscarAnuncios
 */
@WebServlet("/buscarAnuncios.do")
public class BuscarAnuncios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * Pre: BuscarAnuncios funciona con POST.
	 *      POST /BuscarAnuncios.do?tipoAnuncio=
	 *      " 'contenido Json"&=" 'contenido Json"especie&
	 *      palabraClave=" 'contenido Json"
	 *      Ver documentacion para mas detalle.
	 * Post: Busca y envia los anuncios o devuelve error.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Parametros de la peticion
		PrintWriter out = response.getWriter();
		String tipoAnuncio = request.getParameter("tipoAnuncio");
		String especie = request.getParameter("especie");
		String palabrasClave = request.getParameter("palabrasClave");
		
		if(tipoAnuncio==null){
			tipoAnuncio="";
		}
		if(especie==null){
			especie="";
		}
		if(palabrasClave==null){
			palabrasClave="";
		}
		
		/* Llamada a la base de datos, pero pasandole tal vez estas variables. 
		 * Se pasan vacias si quiere
		 * omitirse.
		 */
		try{
		AnuncioPersistencia a = new AnuncioPersistencia();
		List<Anuncio> anuncios = a.searchAnuncios(tipoAnuncio, especie,palabrasClave);
		
		String respuesta = Anuncio.listToJson(anuncios);
		out.println(respuesta);
		response.setStatus(200);
		}
		catch(Exception ex){
			ex.printStackTrace();
			out.println("ERROR DEL SERVIDOR");
			response.setStatus(500);
			
		}
		
	}
	
	/**
	 * El POST no debe hacer nada.
	 */
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA GET PARA OBTENER ANUNCIOS");
		response.setStatus(500);
	}
}
