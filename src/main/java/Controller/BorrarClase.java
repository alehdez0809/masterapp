package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BorrarClase extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection cn = null;
        try (PrintWriter out = response.getWriter()) {
            Base db = new Base();
            try {
                cn = db.Conectar();
                String idClase = request.getParameter("idC");
                try (CallableStatement pst = cn.prepareCall("DELETE FROM Clases WHERE idClase=?")) {
                    pst.setString(1, idClase);
                    pst.executeUpdate();

                    out.println("<script>alert('Clase borrada con éxito');</script>");
                    out.println("<script>window.location.replace('Perfil.jsp');</script>");
                } catch (SQLException e) {
                    System.err.println("SQL Error: " + e.getMessage());
                    out.println("<script>alert('Hubo un error al querer borrar la clase, intente más tarde');</script>");
                    out.println("<script>window.location.replace('Perfil.jsp');</script>");
                }
            } catch (URISyntaxException e) {
                System.err.println("URI Syntax Error: " + e.getMessage());
                out.println("<script>alert('Error en la configuración de la conexión a la base de datos');</script>");
                out.println("<script>window.location.replace('Perfil.jsp');</script>");
            }
        } catch (SQLException e) {
            Logger.getLogger(BorrarClase.class.getName()).log(Level.SEVERE, null, e);
            throw new ServletException("Error al cerrar PrintWriter", e);
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    Logger.getLogger(BorrarClase.class.getName()).log(Level.SEVERE, null, e);
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
        return "Short description";
    }
}
