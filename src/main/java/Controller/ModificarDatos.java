package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Riquelme
 */
public class ModificarDatos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String nombre = request.getParameter("nombre");
            String apellidoP = request.getParameter("apellidoP");
            String apellidoM = request.getParameter("apellidoM");
            String correo = request.getParameter("email");
            String contraN = request.getParameter("password");
            String numero = request.getParameter("numero");
            String delegacion = request.getParameter("delegacion");
            
            Connection cn;
            Base db = new Base();
            cn = db.Conectar();
            
            HttpSession sesion = request.getSession();
            int idUser = (Integer)sesion.getAttribute("id");
            String contraV = (String)sesion.getAttribute("contra");
            String correoV = (String)sesion.getAttribute("correo");
            
            if(contraN==null || contraN.equals("")){
                contraN = contraV;
            }
            
            boolean emailN = true;
            if(correo.equals(correoV)){
                emailN = false;
            }
            
            if(db.modificarDatos(idUser,nombre,apellidoP,apellidoM,numero,delegacion,correo,emailN,contraN)){
                System.out.println("Datos modificados");
                out.println("<script>alert('Datos modificados');</script>");
                
                Usuario us = db.iniciarSesion(correo,contraN);
                sesion.setAttribute("id", us.getId());
                sesion.setAttribute("nombre", us.getNombre());
                sesion.setAttribute("apellidoP",us.getApellidoP());
                sesion.setAttribute("apellidoM",us.getApellidoM());
                sesion.setAttribute("correo",us.getCorreo());
                sesion.setAttribute("contra",us.getContra());
                sesion.setAttribute("delegacion",us.getDel());
                sesion.setAttribute("tipoUser",us.getTipU());
                sesion.setAttribute("telefono",us.getTel());
                
                out.println("<script>window.location.replace('Perfil.jsp');</script>");
            }
            else{
                System.out.println("Error al intentar modificar datos");
                out.println("<script>alert('No se lograron actualizar los datos');</script>");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ModificarDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ModificarDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
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
