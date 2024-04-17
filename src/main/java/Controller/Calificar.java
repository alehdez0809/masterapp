package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Riquelme
 */
public class Calificar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            boolean nuevo = Boolean.parseBoolean(request.getParameter("nuevo"));

            if (nuevo) {
                Connection cn;
                Base db = new Base();
                cn = db.Conectar();

                HttpSession sesion = request.getSession();
                int idEm = (Integer) sesion.getAttribute("id");
                String idRe = request.getParameter("idRe");
                String calificacion = request.getParameter("estrellas");

                try {
                    PreparedStatement pst;
                    pst = cn.prepareStatement("INSERT INTO Calificaciones(calificacion,idUsCalifica,idUsCalificado) VALUES(?,?,?)");
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
                    out.println("<body onLoad=\"javascript:enviarForm();\">\n"
                            + "<form action=\"Mensajeria.jsp\" method=post name=\"reload\">\n"
                            + "<input type=\"hidden\" name=\"idRe\" value=\"" + idRe + "\">\n"
                            + "<input type=\"hidden\" name=\"mat\" value=\"\">\n"
                            + "</form> "
                            + "<script language=\"javascript\">\n"
                            + "function enviarForm()\n"
                            + "{\n"
                            + "document.reload.submit();\n"
                            + "}\n"
                            + "</script>");
                } catch (SQLException e) {
                    System.out.println("Error subiendo calificacion: " + e);
                    out.println("<script>alert('No se ha podido enviar la calificacion, intente mas tarde');</script>");
                    out.println("<script>window.location.replace('Mensajeria.jsp');</script>");
                }
            }
            else if(!nuevo) {
                Connection cn;
                Base db = new Base();
                cn = db.Conectar();

                HttpSession sesion = request.getSession();
                int idEm = (Integer) sesion.getAttribute("id");
                String idRe = request.getParameter("idRe");
                String calificacion = request.getParameter("estrellas");

                try {
                    PreparedStatement pst;
                    pst = cn.prepareStatement("UPDATE Calificaciones SET calificacion=? WHERE idUsCalifica=? AND idUsCalificado=?");
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
                    out.println("<body onLoad=\"javascript:enviarForm();\">\n"
                            + "<form action=\"Mensajeria.jsp\" method=post name=\"reload\">\n"
                            + "<input type=\"hidden\" name=\"idRe\" value=\"" + idRe + "\">\n"
                            + "<input type=\"hidden\" name=\"mat\" value=\"\">\n"
                            + "</form> "
                            + "<script language=\"javascript\">\n"
                            + "function enviarForm()\n"
                            + "{\n"
                            + "document.reload.submit();\n"
                            + "}\n"
                            + "</script>");
                } catch (SQLException e) {
                    System.out.println("Error subiendo calificacion: " + e);
                    out.println("<script>alert('No se ha podido enviar la calificacion, intente mas tarde');</script>");
                    out.println("<script>window.location.replace('Mensajeria.jsp');</script>");
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
