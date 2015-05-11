/*
 * Nombre: BorrarUsuario.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 6-5-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de procesar peticiones de borrado de usuarios.
 * Copyright (C) 2015 Hyena Technologies
 */
package servicios;

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

@WebServlet("/borrarUsuario.do")
public class BorrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Pre: cierto
	 * Post: El POST no debe hacer nada.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("ERROR: USA GET PARA BORRAR UN USUARIO");
		response.setStatus(500);
	}

	/**
	 * Pre: BorrarUsuario funciona con GET.
	 *      GET /BorrarAnuncio.do?mail= 'email de cuenta a borrar'
	 *      Ver documentacion para mas detalle.
	 * Post: Borra el usuario si existe y el logueado es la cuenta
	 * a borrar o informa del error.
	 */
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
