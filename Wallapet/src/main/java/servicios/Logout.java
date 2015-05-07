/*
 * Nombre: Logout.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 5-5-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones de logout del usuario logueado.
 * Copyright (C) 2015 Hyena Technologies
 */

package servicios;


import persistencia.Cuenta;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;



@WebServlet("/logout.do")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * Pre: LoginUsuario funciona con GET
	 *      GET /LoginUsuario.do
	 *      Parametros: sin parametros
	 *      Ver documentacion para mas detalle.
	 * Post: desloguea al usuario logueado o devuelve error.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();


		HttpSession s = request.getSession();
		Cuenta logueado = (Cuenta) s.getAttribute("usuario");
		if(logueado==null){
			out.println("Usuario no logueado");
			response.setStatus(405);

		}
		else{
			out.println("Deslogueado");
			response.setStatus(200);
			s.invalidate();
		}

	}
	
	/**
	 * Pre: cierto
	 * Post: El POST no debe hacer nada.
	 */
	protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA GET PARA LOGOUT");
		response.setStatus(500);
	}
}
