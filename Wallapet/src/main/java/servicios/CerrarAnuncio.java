/*
 * Nombre: CerrarAnuncio.java
 * Version: 0.1
 * Autor: Ismael Rodriguez
 * Fecha 6-5-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones de cerrar anuncios.
 * Copyright (C) 2015 Hyena Technologies
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

@WebServlet("/cerrarAnuncio.do")
public class CerrarAnuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * Pre: cierto
	 * Post: El GET no debe hacer nada.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA POST PARA CERRAR UN ANUNCIO");
		response.setStatus(500);
	}
	
	/**
	 * Pre: CerrarAnuncio sfunciona con POST.
	 *      POST /CerrarAnuncio.do
	 *      Parametros: id = id del anuncio
	 *      Ver documentacion para mas detalle.
	 * Post: Cierra el anuncio o informa del error.
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
