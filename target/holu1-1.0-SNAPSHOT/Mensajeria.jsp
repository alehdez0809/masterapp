<%@page import="java.util.ArrayList"%>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>+terApp</title>
    <link rel="shortcut icon" href="images/Logo.ico" />
    <link rel="stylesheet" href="http://icono-49d6.kxcdn.com/icono.min.css">
    <link rel="stylesheet" href="https://unpkg.com/purecss@1.0.0/build/pure-min.css" integrity="sha384-" crossorigin="anonymous">
    <link href='https://fonts.googleapis.com/css?family=Allerta Stencil' rel='stylesheet'>
    <link rel="stylesheet" href="css/layouts/side-menu.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="css/chat.css">
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
                <li class="pure-menu-item"><a href="Buscar.jsp" class="pure-menu-link"><i class="icono-search"></i>Buscar</a></li>
                <li class="pure-menu-item"><a href="Mensajeria.jsp" class="pure-menu-link"><i class="icono-commentEmpty"></i>Mensajes</a></li>
                <li class="pure-menu-item"><a href="SubirClase.jsp" class="pure-menu-link"><i class="icono-book"></i>Da clases</a></li>
                <li class="pure-menu-item menu-item-divided pure-menu-selected">
                <a href="CerrarSesion" class="pure-menu-link">Cerrar Sesión</a>
                </li>
        </div>
    </div>
    </div>
   
   
    <div class="people-list" id="people-list">
      
      <ul class="list">
        
        <%
            Base db = new Base();
            int idEm = (Integer)sesion.getAttribute("id");
            try{
            Connection cn = db.Conectar();
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM Chat,usuarios,datos WHERE (datos.idDatos=usuarios.idDatos AND usuarios.idUsuario=idUsRe AND idUsEm=?) OR (datos.idDatos=usuarios.idDatos AND usuarios.idUsuario=idUsEm AND idUsRe=?) ORDER BY idChat DESC");
            ps.setInt(1,idEm);
            ps.setInt(2,idEm);
            ResultSet rs = ps.executeQuery();
            ArrayList<Integer> repetidos = new ArrayList<Integer>();
            while(rs.next()){
                if(rs.getInt("idUsEm")==idEm){
                    boolean check = true;
                    for (int i = 0; i < repetidos.size(); i++) {
                            if(rs.getInt("idUsRe")==repetidos.get(i)){
                                check = false;
                            }
                        }
                    if(check){
                        %>        
          <form action="Mensajeria.jsp" name="mensajes" method="post">
          <button type="submit" name="idRe" value="<%out.println(rs.getString("usuarios.idUsuario"));%>" class="btn-link">
          <input type="hidden" name="mat" value="">
          <li class="clearfix">
          <img src="images/chat.png" alt="avatar" />
          <div class="about">
            <div class="name"><%out.println(rs.getString("nombre")+" "+rs.getString("apellidoP"));%></div>
            <div class="status">
              <i class="fa fa-circle online"></i> disponible
            </div>
          </div>
        </li></button></form><%
                        repetidos.add(rs.getInt("idUsRe"));
                    }
                }
                if(rs.getInt("idUsRe")==idEm){
                    boolean check = true;
                    for (int i = 0; i < repetidos.size(); i++) {
                            if(rs.getInt("idUsEm")==repetidos.get(i)){
                                check = false;
                            }
                        }
                    if(check){
                        %>
            <form action="Mensajeria.jsp" name="mensajes" method="post">
          <button type="submit" name="idRe" value="<%out.println(rs.getString("usuarios.idUsuario"));%>" class="btn-link">
          <input type="hidden" name="mat" value="">
           <li class="clearfix">
          <img src="images/chat.png" alt="avatar" />
          <div class="about">
              <div class="name"><%out.println(rs.getString("nombre")+" "+rs.getString("apellidoP"));%></div>
            <div class="status">
              <i class="fa fa-circle online"></i> disponible
            </div>
          </div>
           </li></button></form><%
                        repetidos.add(rs.getInt("idUsEm"));
                    }
                }
        }}catch(Exception e){
            System.out.println("Error seleccionando usuario");
        }%>
        
              </ul>
    </div>
    
    <div class="chat">
      <div class="chat-header clearfix">
        <img src="images/Conec.png" alt="avatar" />
        
        <%
            String idRe = request.getParameter("idRe");
            String nombreRe;
            if(idRe!=null){
            try{
                Connection cn = db.Conectar();
                PreparedStatement ps = cn.prepareStatement("SELECT * FROM datos,usuarios WHERE datos.idDatos=usuarios.idDatos AND usuarios.idUsuario=?");
                ps.setString(1,idRe);
                ResultSet rs = ps.executeQuery();
                rs.next();
                nombreRe = rs.getString("nombre")+" "+rs.getString("apellidoP");
        %>
        
        <div class="chat-about">
            <div class="chat-with"><%out.println(nombreRe);%></div>
          <div class="chat-num-messages"></div>
        </div>
        <%
            ps = cn.prepareStatement("SELECT * FROM Calificaciones WHERE idUsCalifica=? AND idUsCalificado=?");
            ps.setInt(1,idEm);
            ps.setString(2,idRe);
            rs = ps.executeQuery();
            
            if(!rs.next()){
                %>
                <form name="Calificar" method="post" action="Calificar">
  <p class="clasificacion">
      <input type="submit" value="Calificar">
    <input id="radio1" name="estrellas" value="5" type="radio"><!--
    --><label for="radio1">★</label><!--
    --><input id="radio2" name="estrellas" value="4" type="radio"><!--
    --><label for="radio2">★</label><!--
    --><input id="radio3" name="estrellas" value="3" type="radio"><!--
    --><label for="radio3">★</label><!--
    --><input id="radio4" name="estrellas" value="2" type="radio"><!--
    --><label for="radio4">★</label><!--
    --><input id="radio5" name="estrellas" value="1" type="radio"><!--
    --><label for="radio5">★</label>
    
    <input type="hidden" name="idRe" value="<%out.println(idRe);%>">
    <input type="hidden" name="nuevo" value="true">
  </p>
</form>
                <%
            }else{
                %>
                <form name="Calificar" method="post" action="Calificar">
  <p class="clasificacion">
      <input type="submit" value="Calificar">
      <input id="radio1" name="estrellas" value="5" type="radio" <%if(rs.getInt("calificacion")==5){out.println("checked");};%>><!--
    --><label for="radio1">★</label><!--
    --><input id="radio2" name="estrellas" value="4" type="radio" <%if(rs.getInt("calificacion")==4){out.println("checked");};%>><!--
    --><label for="radio2">★</label><!--
    --><input id="radio3" name="estrellas" value="3" type="radio" <%if(rs.getInt("calificacion")==3){out.println("checked");};%>><!--
    --><label for="radio3">★</label><!--
    --><input id="radio4" name="estrellas" value="2" type="radio" <%if(rs.getInt("calificacion")==2){out.println("checked");};%>><!--
    --><label for="radio4">★</label><!--
    --><input id="radio5" name="estrellas" value="1" type="radio" <%if(rs.getInt("calificacion")==1){out.println("checked");};%>><!--
    --><label for="radio5">★</label>
    
    <input type="hidden" name="idRe" value="<%out.println(idRe);%>">
    <input type="hidden" name="nuevo" value="false">
  </p>
</form>
        <%
            }
        %>
      </div> 
      
      <div class="chat-history">
        <ul>
            
        <%
            ps = cn.prepareStatement("SELECT * FROM Chat WHERE (idUsEm=? AND idUsRe=?) OR (idUsEm=? AND idUsRe=?)");
            ps.setInt(1,idEm);
            ps.setString(2,idRe);
            ps.setString(3,idRe);
            ps.setInt(4,idEm);
            rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getInt("idUsEm")==idEm){%>
                
          <li class="clearfix">
            <div class="message-data align-right">
              <span class="message-data-time" ><%out.println(rs.getString("fecha"));%></span> &nbsp; &nbsp;
              <span class="message-data-name" ><%out.println(sesion.getAttribute("nombre")+" "+sesion.getAttribute("apellidoP"));%></span> <i class="fa fa-circle me"></i>
              
            </div>
            <div class="message other-message float-right">
                <%out.println(rs.getString("mensaje"));%>
            </div>
          </li>
          <%}else if(rs.getInt("idUsEm")!=idEm){
          %>
          <li>
            <div class="message-data">
              <span class="message-data-name"><i class="fa fa-circle online"></i><%out.println(nombreRe);%></span>
              <span class="message-data-time"><%out.println(rs.getString("fecha"));%></span>
            </div>
            <div class="message my-message">
                <%out.println(rs.getString("mensaje"));%>
            </div>
          </li>
          <%
            }}
          }catch(Exception e){
                System.out.println("Error en mensajes: "+e);
                out.println("<script>alert('A ocurrido un error, intente de nuevo más tarde')</script>");
            }
          %>
          
        </ul>
        
      </div> 
      
      <div class="chat-message clearfix">
        <form name="EnviarMensaje" action="EnviarMensaje?idRe=<%out.println(idRe);%>" method="post">
            <textarea name="mensaje" id="message-to-send" placeholder ="Escribe tu mensaje" rows="3" maxlength="280" onkeypress="return validarCon(event)"  onpaste="return false" ondrop="return false" required><%out.println(request.getParameter("mat"));%></textarea>
        
        
        <button type="submit" value="Enviar">Enviar</button>
        </form>
      </div> 
      
    </div> 
   <%}%> 

   

<script id="message-template" type="text/x-handlebars-template">
  <li class="clearfix">
    <div class="message-data align-right">
      <span class="message-data-time" >{{time}}, Today</span> &nbsp; &nbsp;
      <span class="message-data-name" >Olia</span> <i class="fa fa-circle me"></i>
    </div>
    <div class="message other-message float-right">
      {{messageOutput}}
    </div>
  </li>
</script>

<script id="message-response-template" type="text/x-handlebars-template">
  <li>
    <div class="message-data">
      <span class="message-data-name"><i class="fa fa-circle online"></i> Vincent</span>
      <span class="message-data-time">{{time}}, Today</span>
    </div>
    <div class="message my-message">
      {{response}}
    </div>
  </li>
</script>
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.0/handlebars.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/list.js/1.1.1/list.min.js'></script>

  

    <script  src="css/js/chat.js"></script>
    </body>
</html>
