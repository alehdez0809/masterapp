<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="Controller.Base"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  try{
    HttpSession sesion = request.getSession();
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
    <title>Bienvenido</title>
    <link rel="stylesheet" href="http://icono-49d6.kxcdn.com/icono.min.css">
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-" crossorigin="anonymous">
    <link href='https://fonts.googleapis.com/css?family=Allerta Stencil' rel='stylesheet'>
    <link rel="stylesheet" href="css/layouts/side-menu.css">
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
                <li class="pure-menu-item"><a href="Buscar.jsp" class="pure-menu-link"><i class="icono-search"></i>Buscar</a></li>
                <li class="pure-menu-item"><a href="Mensajeria.jsp" class="pure-menu-link"><i class="icono-commentEmpty"></i>Mensajes</a></li>
                <li class="pure-menu-item"><a href="SubirClase.jsp" class="pure-menu-link"><i class="icono-book"></i>Da clases</a></li>
                <li class="pure-menu-item menu-item-divided pure-menu-selected">
                <a href="CerrarSesion" class="pure-menu-link">Cerrar Sesión</a>
                </li>
        </div>
    </div>

    <div id="main">
        <div class="header">
            <h1>Bienvenido a +terApp</h1>
            
    </div>

        <div class="content">
            <h2 class="content-subhead">Algunas clases en <%out.println((String)session.getAttribute("delegacion"));%></h2>
            <table>
                <td class="abajo">Área</td>
                <td class="abajo">Clase</td>
                <td class="abajo">Profesor</td>
                <td class="abajo">Telefono</td>
                <td class="abajo">Día</td>
                <td class="abajo">Horario</td>
                <td class="abajo">Rank</td>
                <td class="abajo">Enviar mensaje</td>
                <%
                    Base db = new Base();
                    String delegacion = (String)session.getAttribute("delegacion");
                    try{
                        Connection cn = db.Conectar();
                        PreparedStatement ps = cn.prepareStatement("SELECT usuarios.idUsuario,clase,nombre,apellidoP,telefono,tipoMateria,dias,horas,calificacionP FROM usuarios,Clases,datos,Delegaciones,TipoMateria WHERE Clases.idUsuario=usuarios.idUsuario AND usuarios.idDatos=datos.idDatos AND datos.idDelegacion=Delegaciones.idDelegacion AND Clases.idTipoMateria=TipoMateria.idTipoMateria AND Delegaciones.Delegacion=? ORDER BY calificacionP DESC");
                        ps.setString(1,delegacion);
                        ResultSet rs = ps.executeQuery();
                        int id = (Integer)session.getAttribute("id");
                        int count = 0;
                        while(rs.next() && count<10){
                            if(id!=rs.getInt("idUsuario")){
                                count++;
                        %>
                            <tr class="arriba">
                            <td><%out.println(rs.getString("TipoMateria"));%></td>
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
                            <form name="mensaje" action="Mensajeria.jsp" method="post">
                                <input type="hidden" name="idRe" value="<%out.println(rs.getString("idUsuario"));%>">
                                <input type="hidden" name="mat" value="Hola, me intereso tu clase de <%out.println(rs.getString("clase"));%>">
                                <td><button type="submit" class="btn-link"><i class="icono-comment"></i></button></td>
                            </form>
                            </tr>
                        <%  }
                        }
                    }catch(Exception e){
                        System.out.println("Error: "+e);
                    }
                    %>
            </table> 
           

        </div>
    </div>
</div>



<script src="css/js/ui.js"></script>

</body>
</html>
