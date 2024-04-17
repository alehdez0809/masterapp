package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Calificar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            boolean nuevo = Boolean.parseBoolean(request.getParameter("nuevo"));
            HttpSession sesion = request.getSession();
            int idEm = (Integer) sesion.getAttribute("id");
            String idRe = request.getParameter("idRe");
            String calificacion = request.getParameter("estrellas");

            try {
                Connection cn = new Base().Conectar(); // Conexión puede lanzar URISyntaxException
                try {
                    if (nuevo) {
                        PreparedStatement pst = cn.prepareStatement("INSERT INTO Calificaciones(calificacion,idUsCalifica,idUsCalificado) VALUES(?,?,?)");
                        pst.setString(1, calificacion);
                        pst.setInt(2, idEm);
                        pst.setString(3, idRe);
                        pst.executeUpdate();

                        pst = cn.prepareStatement("SELECT * FROM Calificaciones WHERE idUsCalificado=?");
                        pst.setString(1, idRe);
                        ResultSet rs = pst.executeQuery();
                        int sumaC = 0, sumaN = 0, calificacionP;
                        while (rs.next()) {
                            sumaC += rs.getInt("calificacion");
                            sumaN++;
                        }
                        calificacionP = Math.round(sumaC / sumaN);

                        pst = cn.prepareStatement("UPDATE usuarios SET calificacionP = ? WHERE idUsuario=?");
                        pst.setInt(1, calificacionP);
                        pst.setString(2, idRe);
                        pst.executeUpdate();

                        System.out.println("Calificacion enviada");
                        out.println("<script>alert('Calificacion enviada con exito');</script>");
                        out.println("<script>window.location.replace('Perfil.jsp');</script>");
                    } else {
                        PreparedStatement pst = cn.prepareStatement("UPDATE Calificaciones SET calificacion=? WHERE idUsCalifica=? AND idUsCalificado=?");
                        pst.setString(1, calificacion);
                        pst.setInt(2, idEm);
                        pst.setString(3, idRe);
                        pst.executeUpdate();

                        pst = cn.prepareStatement("SELECT * FROM Calificaciones WHERE idUsCalificado=?");
                        pst.setString(1, idRe);
                        ResultSet rs = pst.executeQuery();
                        int sumaC = 0, sumaN = 0, calificacionP;
                        while (rs.next()) {
                            sumaC += rs.getInt("calificacion");
                            sumaN++;
                        }
                        calificacionP = Math.round(sumaC / sumaN);

                        pst = cn.prepareStatement("UPDATE usuarios SET calificacionP = ? WHERE idUsuario=?");
                        pst.setInt(1, calificacionP);
                        pst.setString(2, idRe);
                        pst.executeUpdate();

                        System.out.println("Calificacion enviada");
                        out.println("<script>alert('Calificacion enviada con exito');</script>");
                        out.println("<script>window.location.replace('Perfil.jsp');</script>");
                    }
                } catch (SQLException e) {
                    System.err.println("SQL Error: " + e.getMessage());
                    out.println("<script>alert('No se ha podido enviar la calificacion, intente mas tarde');</script>");
                    out.println("<script>window.location.replace('Mensajeria.jsp');</script>");
                } finally {
                    if (cn != null) {
                        try {
                            cn.close();
                        } catch (SQLException e) {
                            System.err.println("SQL Error on close: " + e.getMessage());
                        }
                    }
                }
            } catch (URISyntaxException e) {
                System.err.println("URI Syntax Error: " + e.getMessage());
                out.println("<script>alert('Error en la configuración de la conexión a la base de datos');</script>");
                out.println("<script>window.location.replace('Mensajeria.jsp');</script>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Calificar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Calificar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
