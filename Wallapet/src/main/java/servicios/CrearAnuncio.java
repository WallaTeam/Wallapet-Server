/*
 * Nombre: CrearAnuncio.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 3-4-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones Post para crear anuncios.
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

@WebServlet("/crearAnuncio.do")
public class CrearAnuncio extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * Pre: cierto
	 * Post: El GET no debe hacer nada.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA POST PARA INTRODUCIR UN ANUNCIO");
		response.setStatus(500);
	}

	/**
	 * CrearAnuncio funciona con POST.
	 * POST /crearAnuncio.do.
	 * Ver documentacion
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

		//He recibido un anuncio en formato JSON
		String anuncioJson = request.getParameter("anuncio");

		System.out.println("Peticion de " + anuncioJson);
		try{
			Anuncio received = Anuncio.fromJson(anuncioJson);
			received.setEmail(logueado.getEmail());
			received.setEstado("Abierto");
			AnuncioPersistencia persistencia = new AnuncioPersistencia();
			
			//Creamos el anuncio
			persistencia.createAnuncio(received);
			
			//Respondemos
			out.println("OK ANUNCIO CREADO");
			response.setStatus(200);	
		}catch(Exception ex){
			//Ha habido una excepcion
			ex.printStackTrace();
			out.println("SERVER ERROR");
			response.setStatus(500); //Server error
		}	
	}
}
