package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Base {
    
    public Connection Conectar(){
        Connection con = null;
        
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/masterapp", "root", "n0m3l0");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
            
        return con;     
    }
    
    public boolean registroEstudiante (String nom, String aPat, String aMat, String num, String del, String email, String contra) throws SQLException{
        
        Connection con = Conectar();
        PreparedStatement pst;
        
        try{
            pst = con.prepareStatement("SELECT * FROM usuarios");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                if(email.equals(rs.getString("email"))){
                    return false;
                }
            }
        }catch(SQLException e){
            System.out.println("Error: "+e);
            return false;
        }
        
        try {
            pst = con.prepareStatement("CALL Guardar_Usuario(?,?,?,?,?,?,?)");
            pst.setString(1,nom);
            pst.setString(2,aPat);
            pst.setString(3,aMat);
            pst.setString(4,num);
            pst.setString(5,del);
            pst.setString(6,email);
            pst.setString(7,contra);
            
            pst.executeUpdate();
            closeDB(con,pst,null);
            
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public boolean registroClase(int id, String area, String clase, String dia, String horario) throws SQLException{
        
        Connection con = Conectar();
        PreparedStatement pst;
        
        try{
            pst = con.prepareStatement("SELECT * FROM Clases WHERE idUsuario=?");
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                if(clase.equals(rs.getString("clase")) || dia.equals(rs.getString("dias"))){
                    return false;
                }
            }
        }catch(SQLException e){
            System.out.println("Error: "+e);
            return false;
        }
        
        try {
            pst = con.prepareStatement("CALL Guardar_Clase(?,?,?,?,?)");
            pst.setInt(1,id);
            pst.setString(2,clase);
            pst.setString(3,area);
            pst.setString(4,dia);
            pst.setString(5,horario);
            
            pst.executeUpdate();
            closeDB(con,pst,null);
            
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
        
        return true;
    }

    
    public Usuario iniciarSesion(String email, String contra) throws SQLException{
        
        Base db = new Base();
        Connection con = db.Conectar();
        Usuario us = new Usuario();
        try{
        PreparedStatement ps = con.prepareStatement("SELECT * FROM datos, usuarios, Delegaciones "
                + "WHERE usuarios.email = ? AND usuarios.pass = ? AND usuarios.idDatos=datos.idDatos AND Delegaciones.idDelegacion=datos.idDelegacion");
        ps.setString(1,email);
        ps.setString(2,contra);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
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
        }catch(SQLException e){
            System.out.println("Error java: "+e);
        }
        
        return us;
    }
    
    public boolean modificarDatos(int idUser,String nom, String aPat, String aMat, String num, String del, String email, boolean emailN, String contra){
        Connection con = Conectar();
        PreparedStatement pst;
        
        if(emailN){
            try{
                pst = con.prepareStatement("SELECT email FROM usuarios");
                ResultSet rs = pst.executeQuery();
                while(rs.next()){
                    if(email.equals(rs.getString("email"))){
                        System.out.println("Actualizar datos falló: correo existente");
                        return false;
                    }
                }
            }catch(SQLException e){
                System.out.println("Error: "+e);
            }
        }
        
        try {
            pst = con.prepareStatement("CALL Cambiar_Datos(?,?,?,?,?,?,?,?)");
            pst.setInt(1,idUser);
            pst.setString(2,nom);
            pst.setString(3,aPat);
            pst.setString(4,aMat);
            pst.setString(5,num);
            pst.setString(6,del);
            pst.setString(7,email);
            pst.setString(8,contra);
            
            pst.executeUpdate();
            closeDB(con,pst,null);
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }      
    }
    
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