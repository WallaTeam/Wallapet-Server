package servicios;

import com.mysql.jdbc.StringUtils;
import persistencia.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by teruyi on 8/05/15.
 */
@WebServlet("/datosUsuario.do")
public class ComprarAnuncio extends HttpServlet {


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
        try {

            // Comprar si la peticion es correcta.
            String idParameter = request.getParameter("id");
            if (idParameter.equals("null") || idParameter == "") {
                out.println("ERROR: SERVER ERROR");
                response.setStatus(500);

            } else {

                int id = Integer.parseInt(idParameter);

                // Obtenemos el anuncio de la base de datos
                AnuncioPersistencia persistenciaAnuncio = new AnuncioPersistencia();
                CuentaPersistencia persistenciaCuenta = new CuentaPersistencia();
                CompraVentaPersistencia persistenciaCompraVenta;
                persistenciaCompraVenta = new CompraVentaPersistencia();

                Anuncio a = persistenciaAnuncio.getAnuncio(id);
                Cuenta vendedor = persistenciaCuenta.obtenerCuenta(id);
                if (a == null) {

                    //No existe el anuncio a cerrar
                    out.println("ERROR: SERVER ERROR");
                    response.setStatus(500);
                } else if (vendedor == null) {

                    //Es su anuncio, puede cerrarlo

                    out.println("Usuario no encontrado");
                    response.setStatus(404);
                } else {


                    // rutas a las imagenes estandar
                    String ruta = getServletContext().getRealPath("/wallapet.png");
                    String ruta2 = getServletContext().getRealPath("/nd.gif");


                    // enviamos el correo al comprador
                    GeneradorPdf pdf = new GeneradorPdf(a.getTitulo(),
                            a.getTipoIntercambio(), a.getDescripcion(),
                            a.getPrecio(), a.getEspecie(), vendedor.getNombre(),
                            vendedor.getDireccion(), vendedor.getEmail(),
                            vendedor.getTelefono(), a.getRutaImagen(), false,
                            ruta, ruta2);

                    pdf.getPdf();


                    // enviamos el correo al vendedor
                    pdf = new GeneradorPdf(a.getTitulo(), a.getTipoIntercambio(),
                            a.getDescripcion(), a.getPrecio(), a.getEspecie(),
                            logueado.getNombre(), logueado.getDireccion(),
                            logueado.getEmail(), logueado.getTelefono(),
                            a.getRutaImagen(), true, ruta, ruta2);

                    pdf.getPdf();

                    EmailNotifier mail = new EmailNotifier(vendedor.getEmail());
                    mail.sendNotification("Wallapet", "Se adjunta la información" +
                            " de la petición", logueado.getEmail() + ".pdf");
                    mail = new EmailNotifier(logueado.getEmail());
                    mail.sendNotification("Wallapet", "Se adjunta la información" +
                            " de la petición", vendedor.getEmail() + ".pdf");

                    File fichero = new File(vendedor.getEmail() + ".pdf");

                    if (fichero.delete()) {
                        System.out.println("El fichero ha sido borrado " +
                                "satisfactoriamente");
                    } else {
                        System.out.println("El fichero no puede ser borrado");
                    }

                    fichero = new File(logueado.getEmail() + ".pdf");

                    if (fichero.delete()) {
                        System.out.println("El fichero ha sido borrado " +
                                "satisfactoriamente");
                    } else {
                        System.out.println("El fichero no puede ser borrado");
                    }

                    try {
                        persistenciaCompraVenta.crearCompra(id, logueado.getEmail());
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                        out.println("OK");
                        response.setStatus(200);
                    }

                    //Correo enviado
                    out.println("OK");
                    response.setStatus(200);

                }


            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            out.println("ERROR: SERVER ERROR");
            response.setStatus(500);
        }
    }




}
