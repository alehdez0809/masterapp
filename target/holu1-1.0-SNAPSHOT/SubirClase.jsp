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
    <title>Da clases</title>
    <link rel="shortcut icon" href="images/Logo.ico" />
    <link rel="stylesheet" href="http://icono-49d6.kxcdn.com/icono.min.css">
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-" crossorigin="anonymous">
    <link href='https://fonts.googleapis.com/css?family=Allerta Stencil' rel='stylesheet'>
    <link rel="stylesheet" href="css/layouts/side-menu.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <script src="script/validacion.js" type="text/javascript"></script>
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
                <li class="pure-menu-item"><a href="#" class="pure-menu-link"><i class="icono-book"></i>Da clases</a></li>
                <li class="pure-menu-item menu-item-divided pure-menu-selected">
                <a href="CerrarSesion" class="pure-menu-link">Cerrar Sesión</a>
                </li>
        </div>
    </div>

    <div id="main">
        <div class="header">
            <h1>Añadir clase</h1>
            
    </div>
        <form name="subirClase" action="SubirClase.jsp">
        <div class="content">
            <h2 class="content-subbus">Escoge un área.</h2>
            <div class="select">
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
        
    <br>
    <table>
                <td class="lasdos">Materia</td>
                <td class="abajo"><input class="field" id="email" type="text" placeholder="Descripción de tu clase" name="clase" minlength="3" maxlength="45" onkeypress="return validarn(event)" onpaste="return false" ondrop="return false" required></td>
                <tr class="arriba"><td><td></td></td>
                </tr>
            </table>
    <h2 class="content-subbus">Escoge un día</h2>
    
    <div class="select">
    <select name="dia" id="slct">
        <option value="Lunes">Lunes</option>
        <option value="Martes">Martes</option>
        <option value="Miercoles">Miercoles</option>
        <option value="Jueves">Jueves</option>
        <option value="Viernes">Viernes</option>
        <option value="Sabado">Sabado</option>
        <option value="Domingo">Domingo</option>
    </select>
    </div>
        
    <h2 class="content-subbus">Horario</h2>
    <input type="time" name="horaInicio" class="field" required>
    <input type="time" name="horaFin" class="field" required>
    </div>
    </div>
    <br>
     <table>
    <th><input type="submit" value="Agregar clase" name="sub" class="button"></th>     
   </table>
    </form>
    </div>
            <%
            if(request.getParameter("sub")!=null){
                String clase = request.getParameter("clase");
                String area = request.getParameter("area");
                int id = (Integer)session.getAttribute("id");
                String dia = request.getParameter("dia");
                String horario = request.getParameter("horaInicio")+" - "+request.getParameter("horaFin");
                
                if(db.registroClase(id,area,clase,dia,horario)){
                    System.out.println("Registro de clase concluido con exito");
                    out.println("<script>alert('Clase registrada con éxito');</script>");
                }else{
                    out.println("<script>alert('Ya has subido una clase con el mismo nombre');</script>");
                    System.out.println("Error al registrar clase: ya hay una clase con este nombre");
                }                
            }
        %>
<script src="js/ui.js"></script>
</html>