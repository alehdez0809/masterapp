<%@page import="Controller.Usuario"%>
<%@page import="Controller.Base"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
       <meta name="viewport" content="width=device-width, initial-scale=1">
       <title>Inicia sesión</title>
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
	<link rel="stylesheet" type="text/css" href="css/vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
	<link rel="stylesheet" type="text/css" href="css/vendor/animate/animate.css">
	<link rel="stylesheet" type="text/css" href="css/vendor/css-hamburgers/hamburgers.min.css">
	<link rel="stylesheet" type="text/css" href="css/vendor/animsition/css/animsition.min.css">
	<link rel="stylesheet" type="text/css" href="css/vendor/select2/select2.min.css">
	<link rel="stylesheet" type="text/css" href="css/vendor/daterangepicker/daterangepicker.css">
	<link rel="stylesheet" type="text/css" href="css/css/util.css">
	<link rel="stylesheet" type="text/css" href="css/css/main.css">
        <script src="script/validacion.js" type="text/javascript"></script>
    </head>
    <body>
      <div class="limiter">
	<div class="container-login100">
	<div class="wrap-login100">
            <form class="login100-form validate-form">
            <span class="login100-form-title p-b-26">
            +sterApp
            </span>
            <span class="login100-form-title p-b-48">
	<i class="zmdi zmdi-font"></i>
            </span>
        <div class="wrap-input100" >
		<input class="input100" type="text" name="correo" minlength="8" maxlength="40" onkeypress="return validarc(event)" onpaste="return false" ondrop="return false" required>
		<span class="focus-input100" data-placeholder="Email"></span>
	</div>

        <div class="wrap-input100">
            <span class="btn-show-pass">
            <i class="zmdi zmdi-eye"></i>
	</span>
            <input class="input100" type="password" name="contrasena" minlength="6" maxlength="40" onkeypress="return validarCon(event)" onpaste="return false" ondrop="return false" required>
            <span class="focus-input100" data-placeholder="Contraseña"></span>
        </div>
        <div class="container-login100-form-btn">
            <div class="wrap-login100-form-btn">
	<div class="login100-form-bgbtn"></div>
	<button type="submit" name="sub" class="login100-form-btn">Iniciar</button>
	</div>
	</div>
        <div class="text-center p-t-115">
            <span class="txt1">
            ¿No tienes cuenta?
            </span>
            <a class="txt2" href="Registro.jsp">
            Regístrate
            </a>
            </div>
            </form>
           </div>
	</div>
	</div>
	<div id="dropDownSelect1"></div>
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="vendor/animsition/js/animsition.min.js"></script>
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="vendor/select2/select2.min.js"></script>
	<script src="vendor/daterangepicker/moment.min.js"></script>
	<script src="vendor/daterangepicker/daterangepicker.js"></script>
	<script src="vendor/countdowntime/countdowntime.js"></script>
	<script src="js/main.js"></script>
        <%
            if(request.getParameter("sub")!=null){
        Base db = new Base();
        String correo = request.getParameter("correo");
       String contra = request.getParameter("contrasena");
       HttpSession sesion = request.getSession();
       try{
            Usuario us = db.iniciarSesion(correo, contra);
            sesion.setAttribute("id", us.getId());
            sesion.setAttribute("nombre", us.getNombre());
            sesion.setAttribute("apellidoP",us.getApellidoP());
            sesion.setAttribute("apellidoM",us.getApellidoM());
            sesion.setAttribute("correo",us.getCorreo());
            sesion.setAttribute("contra",us.getContra());
            sesion.setAttribute("delegacion",us.getDel());
            sesion.setAttribute("tipoUser",us.getTipU());
            sesion.setAttribute("telefono",us.getTel());
            
            if(correo.equals(request.getSession().getAttribute("correo"))){
                response.sendRedirect("PaginaBienvenida.jsp");
            }
            else{
                out.println("<script>alert('Correo o clave incorrecta')</script>");
            }
       }
       catch(Exception e){
           System.out.println("Error jsp: "+e);
           out.println("<script>alert('A ocurrido un error, intente de nuevo más tarde')</script>");
       }}
       %>
</html>
