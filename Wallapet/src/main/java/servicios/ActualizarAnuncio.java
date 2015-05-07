/*
 * Nombre: ActualizarAnuncio.java
 * Version: 0.1
 * Autor: Luis Pellicer.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para modificar anuncios.
 * Copyright (C) 2015 Hyena Technologies
 *
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


@WebServlet("/ActualizarAnuncio")
public class ActualizarAnuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Pre: parametros !=null
	 * Post: El GET no debe hacer nada.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA POST PARA ACTUALIZAR UN ANUNCIO");
		response.setStatus(500);
	}
	
	/**
	 * Pre: ActualizarAnuncio funciona con POST.
	 *      POST /ActualizarAnuncio.do?anuncio=" 'contenido Json"
	 *      Ver documentacion para mas detalle.
	 * Post: Actualiza el anuncio o informa del error.
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
		String anuncioJson = request.getParameter("anuncio");
		System.out.println("Peticion de actualizar sobre " + anuncioJson);
		try{
			Anuncio received = Anuncio.fromJson(anuncioJson);
			AnuncioPersistencia persistencia = new AnuncioPersistencia();
			
			// Comprobar errores.
			if(persistencia.getAnuncio(received.getIdAnuncio()) != null){
				if(persistencia.updateAnuncio(received.getIdAnuncio(),

						received.getEspecie(), received.getDescripcion(),
						received.getTipoIntercambio(), received.getTitulo(),
						received.getPrecio(), received.getRutaImagen())){
					
					// Respuesta al cliente.
					out.println("OK ANUNCIO MODIFICADO");
					response.setStatus(200);
				}
				else{
					out.println("ERROR, id anuncio erroneo");
					response.setStatus(500);
				}
				
			}
			else{
				out.println("ERROR, id anuncio erroneo");
				response.setStatus(500);
				
			}
		}catch(Exception ex){
			
			//Ha habido una excepcion. Server error.
			ex.printStackTrace();
			out.println("SERVER ERROR");
			response.setStatus(500); 
		}
	}
}
