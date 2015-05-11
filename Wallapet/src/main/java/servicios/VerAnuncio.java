/*
 * Nombre: BuscarAnuncios.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para buscar un anuncio.
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

import persistencia.Anuncio;
import persistencia.AnuncioPersistencia;
import persistencia.Cuenta;

@WebServlet("/verAnuncio.do")
public class VerAnuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Pre: VerAnuncio funciona con GET.
	 *      GET /verAnuncio.do?id=ID_ANUNCIO
	 *      Ver documentacion para mas detalle.
	 * Post: Devuelve los datos del anuncio pedidos en JSON, o error si procede.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		HttpSession s = request.getSession();
		Cuenta logueado = (Cuenta) s.getAttribute("usuario");
		if(logueado==null){
			out.println("Usuario no logueado");
			response.setStatus(405);
			return;
		}

		try {

			String idParameter = request.getParameter("id");
			if (idParameter == null || idParameter == "") {
				out.println("ERROR: PARAMETRO ID PASADO DE FORMA INCORRECTA");
				response.setStatus(500);

			} else {
				int id = Integer.parseInt(idParameter);
				
				// Obtenemos el anuncio de la base de datos
				AnuncioPersistencia persistencia = new AnuncioPersistencia();
				Anuncio result = persistencia.getAnuncio(id);
				if(result!=null){
					
					//Existe el anuncio
					String json = Anuncio.toJson(result);
					out.println(json);
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

	/**
	 * El POST no debe hacer nada.
	 */
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA GET PARA OBTENER UN ANUNCIO");
		response.setStatus(500);
	}
}
