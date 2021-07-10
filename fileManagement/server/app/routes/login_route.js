const path = require('path');

module.exports = app => {
    /**
     * Redirect to main page after introduce username and password
     */
     app.get("/login", (req, res) => {   
        hashCode = function(s){
            return s.split("").reduce(function(a,b){a=((a<<5)-a)+b.charCodeAt(0);return a&a},0);              
        }
    
        if (-1770856881 == hashCode(req.query.passParam)){
            res.sendFile(path.join(__dirname,"../views/index.html"));
           console.log("Sesión iniciada correctamente");
         }else{
            console.log("Datos no correctos");
        }
    });
}