package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModificarDatos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession sesion = request.getSession();
            String nombre = request.getParameter("nombre");
            String apellidoP = request.getParameter("apellidoP");
            String apellidoM = request.getParameter("apellidoM");
            String correo = request.getParameter("email");
            String contraN = request.getParameter("password");
            String numero = request.getParameter("numero");
            String delegacion = request.getParameter("delegacion");
            int idUser = (Integer)sesion.getAttribute("id");
            String contraV = (String)sesion.getAttribute("contra");
            String correoV = (String)sesion.getAttribute("correo");

            if (contraN == null || contraN.isEmpty()) {
                contraN = contraV;
            }
            boolean emailN = !correo.equals(correoV);

            Connection cn = null;
            try {
                Base db = new Base();
                cn = db.Conectar();
                if (db.modificarDatos(idUser, nombre, apellidoP, apellidoM, numero, delegacion, correo, emailN, contraN)) {
                    out.println("<script>alert('Datos modificados'); window.location.replace('Perfil.jsp');</script>");
                } else {
                    throw new SQLException("Error al intentar modificar datos");
                }
            } catch (SQLException | URISyntaxException e) {
                Logger.getLogger(ModificarDatos.class.getName()).log(Level.SEVERE, null, e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en la operación de base de datos");
            } finally {
                if (cn != null) {
                    try {
                        cn.close();
                    } catch (SQLException e) {
                        Logger.getLogger(ModificarDatos.class.getName()).log(Level.SEVERE, null, e);
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Modifica datos del usuario";
    }
}
