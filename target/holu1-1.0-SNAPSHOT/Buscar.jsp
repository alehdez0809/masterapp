<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="Controller.Base"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion = request.getSession();
    try{
        int tipoU = (Integer)sesion.getAttribute("tipoUser");
        if(tipoU!=2){
    %>
    <jsp:forward page="index.html">
    <jsp:param name="error" value="Es
               obligatorio identificarse"/>
</jsp:forward>
    <%
        }}catch(Exception e){
            System.out.println("Error: "+e);%>
    <jsp:forward page="index.html">
    <jsp:param name="error" value="Es
               obligatorio identificarse"/>
</jsp:forward>
       <% }
        %>
<!DOCTYPE html>
<html>
     <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="A layout example with a side menu that hides on mobile, just like the Pure website.">
    <title>+terApp</title>
    <link rel="shortcut icon" href="images/Logo.ico" />
    <link rel="stylesheet" href="http://icono-49d6.kxcdn.com/icono.min.css">
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-" crossorigin="anonymous">
    <link href='https://fonts.googleapis.com/css?family=Allerta Stencil' rel='stylesheet'>
    <link rel="stylesheet" href="css/layouts/side-menu.css">
     <link rel="stylesheet" href="css/style.css">
       <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
       <script src="script/validacion.js" type="text/javascript"></script>
       <link rel="stylesheet" href="css/Mensajeria.css">
</head>
<body>

<div id="layout">
    <a href="#menu" id="menuLink" class="menu-link">
        <span></span>
    </a>
    <div id="menu">
        <div class="pure-menu">
            <a class="pure-menu-heading" href="PaginaBienvenida.jsp">+terApp</a>
            <ul class="pure-menu-list">
                <li class="pure-menu-item"><a href="Perfil.jsp" class="pure-menu-link"><i class="icono-user"></i>Perfil</a></li>
                <li class="pure-menu-item"><a href="#" class="pure-menu-link"><i class="icono-search"></i>Buscar</a></li>
                <li class="pure-menu-item"><a href="Mensajeria.jsp" class="pure-menu-link"><i class="icono-commentEmpty"></i>Mensajes</a></li>
                <li class="pure-menu-item"><a href="SubirClase.jsp" class="pure-menu-link"><i class="icono-book"></i>Da clases</a></li>
                <li class="pure-menu-item menu-item-divided pure-menu-selected">
                <a href="#" class="pure-menu-link">Cerrar Sesión</a>
                </li>
        </div>
    </div>

    <div id="main">
        <div class="header">
            <h1>Búsqueda</h1>
            
    </div>

        <div class="content">
            <h2 class="content-subbus">Escoge un área.</h2>
         <div class="wrap">
    <div class="RadioBtnsWrap">
        <form method="post" name="buscar" action="Buscar.jsp">
        <select name="area" id="slct">
        <%
                        Base db = new Base();
                        try{
                            Connection cn = db.Conectar();
                            PreparedStatement ps = cn.prepareStatement("SELECT * FROM TipoMateria");
                            ResultSet rs = ps.executeQuery();
                            while(rs.next()){
                %>   
                    <option value="<%out.println(rs.getString("idTipoMateria"));%>"><%out.println(rs.getString("tipoMateria"));%></option>
                <%
                            }
                        }catch(Exception e){
                            System.out.println("Error: "+e);
                        }
                %>
        </select>
    </div>
</div>    
   
            
    <h2 class="content-subbus2">Utiliza palabra(s) clave</h2>
   
        <input class="field" id="email" type="text" placeholder="Descripción de tu clase" name="clase" minlength="3" maxlength="45" onkeypress="return validarn(event)" onpaste="return false" ondrop="return false" required>
    
    
        </div>
        
    </div>
   <table>
    <th><input type="submit" name="Busqueda" value="Buscar" class="button"></th>     
   </table>
                </form>
    <br>
    <br>
    <br>
    <table>
                <td class="abajo">Clase</td>
                <td class="abajo">Profesor</td>
                <td class="abajo">Telefono</td>
                <td class="abajo">Día</td>
                <td class="abajo">Horario</td>
                <td class="abajo">Rank</td>
                <td class="abajo">Delegación</td>
                <td class="abajo">Enviar mensaje</td>
                <tr class="arriba">
                    <%
        if(request.getParameter("Busqueda")!=null){
                String area = request.getParameter("area");
                String clase = request.getParameter("clase");
                int id = (Integer)sesion.getAttribute("id");
                
                try{
                    Connection cn = db.Conectar();
                    PreparedStatement ps = cn.prepareStatement("SELECT usuarios.idUsuario,clase,nombre,apellidoP,telefono,Delegacion,dias,horas,calificacionP FROM usuarios,Clases,datos,Delegaciones WHERE Clases.idUsuario=usuarios.idUsuario AND usuarios.idDatos=datos.idDatos AND datos.idDelegacion=Delegaciones.idDelegacion AND Clases.idTipoMateria=? AND Clases.clase LIKE '%"+clase+"%' ORDER BY calificacionP DESC");
                    ps.setString(1,area);
                    ResultSet rs = ps.executeQuery();
                    
                    while(rs.next()){
                            if(id!=rs.getInt("idUsuario")){
                        %>
                            <tr class="arriba">
                            <td><%out.println(rs.getString("clase"));%></td>
                            <td><%out.println(rs.getString("nombre")+" "+rs.getString("apellidoP"));%></td>
                            <td><%out.println(rs.getString("telefono"));%></td>
                            <td><%out.println(rs.getString("dias"));%></td>
                            <td><%out.println(rs.getString("horas"));%></td>
                            <td><%if(rs.getInt("calificacionP")==0){out.println("Sin calificaciones");}
                                else{for (int i = 0; i < rs.getInt("calificacionP"); i++) {
                                        out.println("★");
                                    }
                                }%></td>
                            <td><%out.println(rs.getString("Delegacion"));%></td>
                            <form name="mensaje" action="Mensajeria.jsp" method="post">
                                <input type="hidden" name="idRe" value="<%out.println(rs.getString("idUsuario"));%>">
                                <input type="hidden" name="mat" value="Hola, me intereso tu clase de <%out.println(rs.getString("clase"));%>">
                            <td><button type="submit" class="btn-link"><i class="icono-comment"></i></button></td>
                            </form>
                            </tr>
                        <%  }
                        }
                    
                }catch(Exception e){
                    System.out.println("Error jsp: "+e);
                }
            }
    %>
            </table>
    </div>
 




<script src="js/ui.js"></script>
</html>
