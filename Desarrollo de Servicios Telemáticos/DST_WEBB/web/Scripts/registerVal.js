//en esta función comprobamos que los parámetros del formulario de registro son 
//correctos
function isValid() {
    ok = true;
    alertMess = '';
    var userPass = document.forms["registerForm"]["userPass"].value;
    var userPassConf = document.forms["registerForm"]["userPassConf"].value;
    if (userPass!=userPassConf){
        ok = false;
        alertMess = alertMess + "Las contraseñas no coinciden\n";
    }
    
    var userRecoverKey = document.forms["registerForm"]["passRecover"].value;
    if (userPass==userPassConf && userPass==userRecoverKey){
        ok = false;
        alertMess = alertMess + "La clave debe de ser distinta a la contraseña\n";
    }
    //si algún campo no es válido, entonces no enviamos la petición
    if (ok == false) {
        alert(alertMess);
        return false;
    } else {
        return true;
    }

}


