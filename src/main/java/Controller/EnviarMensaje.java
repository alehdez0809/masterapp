package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Riquelme
 */
public class EnviarMensaje extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String mensaje = request.getParameter("mensaje");
            Connection cn;
            Base db = new Base();
            cn = db.Conectar();
            
            HttpSession sesion = request.getSession();
            int idEm = (Integer)sesion.getAttribute("id");
            String idRe = request.getParameter("idRe");
            
            try{
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date today = (Date) Calendar.getInstance().getTime();
            String reportDate = df.format(today);
            
            PreparedStatement pst;
            pst = cn.prepareStatement("CALL Enviar_Mensaje(?,?,?,?)");
            pst.setInt(1,idEm);
            pst.setString(2,idRe);
            pst.setString(3,mensaje);
            pst.setString(4,reportDate);
            
            pst.executeUpdate();
            closeDB(cn,pst,null);
            
            System.out.println("Mensaje enviado");
            out.println("<body onLoad=\"javascript:enviarForm();\">\n" +
                        "<form action=\"Mensajeria.jsp\" method=post name=\"reload\">\n" +
                        "<input type=\"hidden\" name=\"idRe\" value=\""+idRe+"\">\n" +
                        "<input type=\"hidden\" name=\"mat\" value=\"\">\n" +
                        "</form> " +
                        "<script language=\"javascript\">\n" +
                        "function enviarForm()\n" +
                        "{\n" +
                        "document.reload.submit();\n" +
                        "}\n" +
                        "</script>");
            
            }catch(SQLException e){
                System.out.println("Error al enviar mensaje: "+e);
                out.println("<script>alert('Error al intentar enviar mensaje');</script>");
                out.println("<script>window.location.replace('Mensajeria.jsp');</script>");
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

    private void closeDB(Connection con, PreparedStatement pst, ResultSet rs) throws SQLException{
        if(con!=null){
            con.close();
        }
        if(pst!=null){
            pst.close();
        }
        if(rs!=null){
            rs.close();
        }
    }

}
