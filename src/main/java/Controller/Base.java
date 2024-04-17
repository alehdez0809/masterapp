package Controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Base {
    
    public Connection Conectar()throws URISyntaxException, SQLException{
        String dbUrl = System.getenv("JAWSDB_URL");
        
        if (dbUrl == null){
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/masterapp", "root", "n0m3l0");
        }
        
            URI dbUri = new URI(dbUrl);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbConnUrl = "jdbc:mysql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        return DriverManager.getConnection(dbConnUrl, username, password);
    }
    
    public boolean registroEstudiante (String nom, String aPat, String aMat, String num, String del, String email, String contra) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = Conectar();
            pst = con.prepareStatement("SELECT * FROM usuarios WHERE email = ?");
            pst.setString(1, email);
            rs = pst.executeQuery();
            
            if(rs.next()){
                // Si encuentra un email igual en la base de datos, retorna falso.
                return false;
            }
            
            pst.close(); // Cierra el PreparedStatement anterior antes de crear uno nuevo
            
            pst = con.prepareStatement("CALL Guardar_Usuario(?,?,?,?,?,?,?)");
            pst.setString(1, nom);
            pst.setString(2, aPat);
            pst.setString(3, aMat);
            pst.setString(4, num);
            pst.setString(5, del);
            pst.setString(6, email);
            pst.setString(7, contra);
            
            pst.executeUpdate();
            
            return true;
        } catch (URISyntaxException | SQLException e) {
            System.out.println("Error: " + e);
            return false;
        } finally {
            closeDB(con, pst, rs);
        }
    }
    
    public boolean registroClase(int id, String area, String clase, String dia, String horario) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = Conectar();
            pst = con.prepareStatement("SELECT * FROM Clases WHERE idUsuario=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                if (clase.equals(rs.getString("clase")) || dia.equals(rs.getString("dias"))) {
                    return false; // Clase ya existe
                }
            }
            rs.close(); // Cierra ResultSet antes de reutilizar pst
            pst.close(); // Cierra PreparedStatement antes de reutilizar

            pst = con.prepareStatement("CALL Guardar_Clase(?,?,?,?,?)");
            pst.setInt(1, id);
            pst.setString(2, clase);
            pst.setString(3, area);
            pst.setString(4, dia);
            pst.setString(5, horario);
            pst.executeUpdate();

            return true;
        } catch (SQLException | URISyntaxException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            closeDB(con, pst, rs);
        }
    }


    
    public Usuario iniciarSesion(String email, String contra) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario us = new Usuario();

        try {
            con = Conectar();
            ps = con.prepareStatement("SELECT * FROM datos, usuarios, Delegaciones WHERE usuarios.email = ? AND usuarios.pass = ? AND usuarios.idDatos = datos.idDatos AND Delegaciones.idDelegacion = datos.idDelegacion");
            ps.setString(1, email);
            ps.setString(2, contra);
            rs = ps.executeQuery();

            if (rs.next()) {
                us.setId(rs.getInt("idUsuario"));
                us.setNombre(rs.getString("nombre"));
                us.setApellidoP(rs.getString("apellidoP"));
                us.setApellidoM(rs.getString("apellidoM"));
                us.setCorreo(rs.getString("email"));
                us.setContra(rs.getString("pass"));
                us.setTel(rs.getString("telefono"));
                us.setDel(rs.getString("Delegacion"));
                us.setTipU(rs.getInt("idTipoUsuario"));
            }

            return us;
        } catch (SQLException | URISyntaxException e) {
            System.out.println("Error java: " + e);
            return null;
        } finally {
            closeDB(con, ps, rs);
        }
    }

    
    public boolean modificarDatos(int idUser, String nom, String aPat, String aMat, String num, String del, String email, boolean emailN, String contra) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = Conectar();
            if (emailN) {
                pst = con.prepareStatement("SELECT email FROM usuarios");
                rs = pst.executeQuery();
                while (rs.next()) {
                    if (email.equals(rs.getString("email"))) {
                        System.out.println("Actualizar datos falló: correo existente");
                        return false;
                    }
                }
                rs.close(); // Cierra ResultSet
                pst.close(); // Cierra PreparedStatement
            }

            pst = con.prepareStatement("CALL Cambiar_Datos(?,?,?,?,?,?,?,?)");
            pst.setInt(1, idUser);
            pst.setString(2, nom);
            pst.setString(3, aPat);
            pst.setString(4, aMat);
            pst.setString(5, num);
            pst.setString(6, del);
            pst.setString(7, email);
            pst.setString(8, contra);
            pst.executeUpdate();

            return true;
        } catch (SQLException | URISyntaxException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        } finally {
            closeDB(con, pst, rs);
        }
    }

    
    private void closeDB(Connection con, PreparedStatement pst, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar ResultSet: " + e.getMessage());
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar Connection: " + e.getMessage());
            }
        }
    }

}