/*
 * Nombre: LoginUsuario.java
 * Version: 0.1
 * Autor: Ismael Rodriguez.
 * Fecha 5-5-2015
 * Descripcion: Este fichero implementa el servlet del servidor que se encarga
 *              de loguear a un usuario.
 * Copyright (C) 2015 Hyena Technologies
 */
package servicios;

import persistencia.Cuenta;
import persistencia.CuentaPersistencia;
import persistencia.DatosLogin;
import persistencia.Utiles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;


@WebServlet("/loginUsuario.do")
public class LoginUsuario extends HttpServlet {

    /**
     * Pre: cierto
     * Post: El GET no debe hacer nada.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("ERROR: USA POST PARA LOGUEAR UN USUARIO");
        response.setStatus(500);
    }

    /**
     * Pre: LoginUsuario funciona con POST.
     *      POST /LoginUsuario.do
     *      Parametros: mail: mail de la cuenta
     *      pass: contraseña de la cuenta
     *      Ver documentacion para mas detalle.
     * Post: loguea un usuario o devuelve error.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        PrintWriter out = response.getWriter();

        String json = request.getParameter("login");
        DatosLogin dl = DatosLogin.fromJson(json);
        dl.setPass(Utiles.generateHash(dl.getPass()));



        try{

            CuentaPersistencia persistencia = new CuentaPersistencia();

            Cuenta c = persistencia.loginCuenta(dl);
            if(c!=null){
                HttpSession s = request.getSession();
                s.setAttribute("usuario",c);
                out.println(Cuenta.toJson(c));
                response.setStatus(200);

            }
            else{
                out.println("Error de autentificación: usuario o contraseña " +
                        "incorrectos");
                response.setStatus(403);
            }

        }catch(Exception ex){

            //Ha habido una excepcion
            ex.printStackTrace();
            out.println("SERVER ERROR");

            //Server error
            response.setStatus(500);
        }
    }
}
