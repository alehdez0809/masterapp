
function validarn(e){
           var teclad = (document.all)
           ? e.keyCode : e.which;
           if(teclad==8) return true;
           patron = /[A-Za-zÑñóáíéúÁÉÍÓÚÜü ]/;
           tec=String.fromCharCode(teclad);
           return patron.test(tec);
 }
function validarCalle(e){
           var teclad = (document.all)
           ? e.keyCode : e.which;
           if(teclad==8) return true;
           patron = /[A-Za-zÑñóáíéúÁÉÍÓÚÜü#0-9 ]/;
           tec=String.fromCharCode(teclad);
           return patron.test(tec);
 }
function validarp(e){
           var teclad = (document.all)
           ? e.keyCode : e.which;
           if(teclad==8) return true;
           patron = /[A-Za-zÑñóáíéúÁÉÍÓÚÜü ]/;
           tec=String.fromCharCode(teclad);
           return patron.test(tec);
 }
function validarA(e){
           var teclad = (document.all)
           ? e.keyCode : e.which;
           if(teclad==8) return true;
           patron = /[0-9]/;
           tec=String.fromCharCode(teclad);
           return patron.test(tec);
 }

function validarc(e){
           var teclad = (document.all)
           ? e.keyCode : e.which;
           if(teclad==8) return true;
           patron = /[!#$%&*+-./0-9=?@A-Z^_a-z{|}~]/;
           tec=String.fromCharCode(teclad);
           return patron.test(tec);
 }
function validarCon(e){
           var teclad = (document.all)
           ? e.keyCode : e.which;
           if(teclad==8) return true;
           patron = /[!+-./0-9=?@A-Z^_a-z{|} ]/;
           tec=String.fromCharCode(teclad);
           return patron.test(tec);
 }


function verificar1(){
    
    
    valido=true;


        var contra= document.inicio.con.value;
        var correo1= document.inicio.correo.value;
        
        
        
        document.inicio.con.value=contra.trim();
        document.inicio.correo.value=correo1.trim();


//        if ()
          

        if(correo1.charAt(correo1.length-1)=="." || correo1.charAt(0)=="."  ){
                  alert("Escribe el correo correctamente");
                  valido=false;
        }
        else{

                  var puntos=0;

                  for (var i = 0; i < correo1.length; i++) {
                           if(correo1.charAt(i)=="@"){
                               if (correo1.charAt(i+1)=="." || correo1.charAt(i-1)=="."){
                                        alert("Escribe el correo correctamente");
                                        valido=false;
                               }

                               for (var j = i; j < correo1.length; j++) {
                                        if(correo1.charAt(j)=="."){
                                            puntos+=1;
                                        }
                               }
                           }
                  }
                  if(puntos<1){
                           alert("Escribe el correo correctamente");
                           valido=false;
                  }

        }



        return valido;


}

function obtener(){



        var contra= document.registro.con.value;
        var correo1= document.registro.correo.value;
        var nomU= document.registro.nomU.value;
        var tel= document.registro.tel.value;
        
        
        document.registro.con.value=contra.trim();
        document.registro.correo.value=correo1.trim();
        document.registro.nomU.value=nomU.trim();
        document.registro.tel.value=tel.trim();


          if(correo1.charAt(correo1.length-1)=="." || correo1.charAt(0)=="."  ){
                  alert("Escribe el correo correctamente");
                  document.registro.correo.focus();
                            return false;
        }
        else{

                  var puntos=0;

                  for (var i = 0; i < correo1.length; i++) {
                           if(correo1.charAt(i)=="@"){
                               if (correo1.charAt(i+1)=="." || correo1.charAt(i-1)=="."){
                                        alert("Escribe el correo correctamente");
                                        document.registro.correo.focus();
                            return false;
                               }

                               for (var j = i; j < correo1.length; j++) {
                                        if(correo1.charAt(j)=="."){
                                            puntos+=1;
                                        }
                               }
                           }
                  }
                  if(puntos<1){
                           alert("Escribe el correo correctamente");
                           document.registro.correo.focus();
                            return false;
                  }

        }
                
          if(correo1.length < 5){
			alert("Ingresa tu correo correctamente");
			document.registro.correo.focus();
			return false;
        }
          if(nomU.length < 3){
			alert("Tu nombre de usuario debe tener al menos 3 caracteres");
			document.registro.nomU.focus();
			return false;
        }
        
          if(contra.length < 6){
			alert("Tu contraseña debe tener al menos 6 caracteres");
			document.registro.con.focus();
			return false;
        }
          

        
        

    var intDia;
    var intMes;
    var intAnio;
    var strAnio;
    var dtmHoy = new Date();
    

    var appat=document.registro.appat;
    var apmat=document.registro.apmat;
    var nom=document.registro.nom;
    var anio = document.registro.ano;
    var cp = document.registro.cp;
    var dir = document.registro.dir;


	appat.value  = (appat.value.toLowerCase()).trim();


	apmat.value = (apmat.value.toLowerCase()).trim();
	nom.value  = (nom.value.toLowerCase()).trim();
	cp.value  = (cp.value.toLowerCase()).trim();
	dir.value  = (dir.value.toLowerCase()).trim();

        if(nom.value.length < 3){
			alert("Tu nombre debe tener al menos 3 caracteres");
			nom.focus();
			return false;
        }

        if(appat.value.length < 3){
			alert("Tu apellido paterno debe tener al menos 3 caracteres");
			appat.focus();
			return false;
        }
        if(apmat.value.length < 3){
			alert("Tu apellido materno debe tener al menos 3 caracteres");
			apmat.focus();
			return false;
        }
        var valido=true;
        for (var i = 0; i < correo1.length; i++) {
            letra = correo1.charAt(i);
            patron = /[!#$%&*+-./0-9=?@A-Z^_a-z{|}~]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        for (var i = 0; i < nom.value.length; i++) {
            letra = nom.value.charAt(i);
            patron = /[A-Za-zÑñóáíéúÁÉÍÓÚÜü]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        for (var i = 0; i < appat.value.length; i++) {
            letra = appat.value.charAt(i);
            patron = /[A-Za-zÑñóáíéúÁÉÍÓÚÜü ]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        for (var i = 0; i < apmat.value.length; i++) {
            letra = apmat.value.charAt(i);
            patron = /[A-Za-zÑñóáíéúÁÉÍÓÚÜü ]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        for (var i = 0; i < nomU.length; i++) {
            letra = nomU.charAt(i);
            patron = /[!+-./0-9=?@A-Z^_a-z{|}]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        for (var i = 0; i < anio.value.length; i++) {
            letra = anio.value.charAt(i);
            patron = /[0-9]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        intDia  = parseInt(document.registro.dia.options[document.registro.dia.selectedIndex].value);
        for (var i = 0; i < intDia.length; i++) {
            letra = intDia.charAt(i);
            patron = /[0-9]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        for (var i = 0; i < tel.length; i++) {
            letra = tel.charAt(i);
            patron = /[0-9]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        for (var i = 0; i < cp.value.length; i++) {
            letra = cp.value.charAt(i);
            patron = /[0-9]/;
            if(! patron.test(letra)) {
                
                valido= false;
            }
        }
        if (!valido) {
            alert("Caracteres invalidos");
            return false;
    }
        
        

       

        if(anio.value == "" || anio.value.length < 4 )
		{
			alert("Ingresa el año de nacimiento, con 4 digitos")
			anio.focus()
			return false;
		}

        intAnio = parseInt(anio.value);



        if(intAnio < (dtmHoy.getFullYear() - 120) || intAnio > dtmHoy.getFullYear())
		{
			alert("El año de la fecha de nacimiento debe estar entre " + (dtmHoy.getFullYear() - 120)+ " y " + dtmHoy.getFullYear());
			document.registro.ano.focus();
			return false;
                        
		}
        intMes  = parseInt(document.registro.mes.options[document.registro.mes.selectedIndex].value) - 1;
        intDia  = parseInt(document.registro.dia.options[document.registro.dia.selectedIndex].value);


        if(isNaN(intDia)){
                        alert("Escribe correctamente el dÍa");
			document.registro.dia.focus();
			return false;
        }
        if(isNaN(intMes)){
                        alert("Escribe correctamente el mes");
			
			return false;
        }
        
        if(!(ValidaFecha(intDia, intMes, intAnio, "La fecha de nacimiento no es válida"))){
			return false;
		}

        if(ComparaFechas(intDia, intMes, intAnio, dtmHoy.getDate(), dtmHoy.getMonth(), dtmHoy.getFullYear()) == 1){
//			alert("La Fecha de Nacimiento no Puede ser Mayor a la Fecha Actual")
			alert("La fecha de nacimiento no es válida")
			return false;
		}



		var sSexo = "";

		if(document.registro.gender[0].checked)
	   	{
	       sSexo = "&strSexo=" + document.registro.gender[0].value;
		   sSexoA=document.registro.gender[0].value;
	   	}
	   	else if(document.registro.gender[1].checked)
	   	{
	       sSexo = "&strSexo=" + document.registro.gender[1].value;
		   sSexoA=document.registro.gender[1].value;
	   	}
		else
		{
		   alert ("Seleccione el sexo");
		   return false;
		   //sSexo = "&strSexo="
		}

	    if (tel.length<10){
                alert("Tu número de telefono debe de ser a 10 digitos");
			document.registro.tel.focus();
			return false;
            }else if(isNaN(tel)){
                alert("Escribe unicamente números");
			document.registro.tel.focus();
			return false;
                        
                
        
            }else{
                var gui=0;
                
                for (var i = 0; i < tel.length; i++) {
                    
                    if(tel.charAt(i)=="-"){
                        gui++;
                    }
                }
                if (gui>=1) {
                    alert("Escribe unicamente números");
			document.registro.tel.focus();
			return false;
                }
            }
            
            if(cp.value.length < 5){
			alert("El codigo postal debe tener 5 números");
			cp.focus();
			return false;
            }
            




        return true;
}


function ValidaFecha(dia, mes, anio, mensaje){
    var fecha = new Date(anio,mes,dia);

    if(fecha.getYear() < 100 || fecha.getYear() >= 2000)
        var tmp_anio= (fecha.getYear() < 100) ?  1900 + fecha.getYear() : fecha.getYear();

    else if(fecha.getYear() >= 100 && fecha.getYear() < 200)
        var tmp_anio= 1900 + fecha.getYear();
	else
		var tmp_anio= fecha.getYear();

    var fecha1 = dia + "/" + mes + "/" + anio;
    var fecha2 = fecha.getDate() + "/" + fecha.getMonth() + "/" + tmp_anio;

    if(fecha1 != fecha2){
		alert(mensaje);
		return false;
	}
   return true;
}

function valCompra(){
    var tel= document.formulario.tel.value;
    document.formulario.tel.value=tel.trim();
    
    if (tel.length<3){
                alert("Tu codigo debe tener al menos 3 digitos");
			document.formulario.tel.focus();
			return false;
            }else if(isNaN(tel)){
                alert("Escribe unicamente números");
			document.formulario.tel.focus();
			return false;
                        
                
        
            }else{
                var gui=0;
                
                for (var i = 0; i < tel.length; i++) {
                    
                    if(tel.charAt(i)=="-"){
                        gui++;
                    }
                }
                if (gui>=1) {
                    alert("Escribe unicamente números");
			document.formulario.tel.focus();
			return false;
                }
            }
            
    return true;
}
function ComparaFechas(dia1, mes1, anio1, dia2, mes2, anio2){
    var fecha1 = new Date(anio1,mes1,dia1);
    var fecha2 = new Date(anio2,mes2,dia2);

    if(fecha1.getTime() == fecha2.getTime()){
        return (0);
    }
    else if (fecha1.getTime() < fecha2.getTime()){
        return (-1);
    }
    else{
        return (1);
    }
}