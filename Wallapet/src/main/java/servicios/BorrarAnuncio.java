/*
 * Nombre: BorrarAnuncio.java
 * Version: 0.1
 * Autor: Luis Pellicer.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para borrar anuncios.
 * Copyright (C) 2015 Hyena Technologies
 */
package servicios;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import persistencia.AnuncioPersistencia;
import persistencia.Cuenta;


@WebServlet("/BorrarAnuncio")
public class BorrarAnuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Pre: parametros !=null
	 * Post: El GET no debe hacer nada.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA POST PARA BORRAR UN ANUNCIO");
		response.setStatus(500);
	}
	
	/**
	 * Pre: BorrarAnuncio funciona con POST.
	 *      POST /BorrarAnuncio.do
	 *      Parametros: id = id del anuncio
	 *      Ver documentacion para mas detalle.
	 * Post: Borra el anuncio o informa del error.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
				
				if(persistencia.deleteAnuncio(id)){
					
					//Existe el anuncio
					response.setStatus(200);
				}
				else{
					
					// Si no existe, devolvemos 404
					out.println("ERROR: ANUNCIO NO ENCONTRADO");
					response.setStatus(404);
				}
				
			}
		} catch (Exception ex) {
			out.println("ERROR: SERVER ERROR");
			response.setStatus(500);
		}

	}

}
