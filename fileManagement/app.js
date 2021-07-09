const express = require('express')
const multer  = require('multer')
const path = require('path')
const PORT = process.env.PORT || 3000;

//Para cargar archivos al servidor
const fileUpload = require('express-fileupload')

//para crear directorios en el servidor
const fs = require('fs')

const app = express()

app.use(fileUpload())

app.get("/", (req, res) => res.sendFile(path.join(__dirname, "src","login.html")));

/**
 * METHOD: POST
 * It's used to post a new file at the selected 
 */
app.post('/upload', (req, res) => {
    let file = req.files.file;
    let name = "./files/" + req.query.dirName;
    file.mv(`./files/${file.name}`, err => {
        if (err) return res.status(500).send({message : err})
        
        return res.status(200).send({message : 'File Upload'})
    })
});

/**
 * Method: Get
 * It's used to create a new directory on server
 */
app.get('/createNewDir', (req, res) => {
    let name = "./files/" + req.query.dirName;
    if (fs.existsSync(name)){
        console.log("Ya existe un directorio con este nombre");
    }else{
        fs.mkdir(name, function(err){
            if(err){
                throw('Error: ' + err);
            }
            console.log("Directorio creado")
        })
    }
});

/**
 * 
 */
app.get("/login", (req, res) => {   

    hashCode = function(s){
        return s.split("").reduce(function(a,b){a=((a<<5)-a)+b.charCodeAt(0);return a&a},0);              
    }

    if (-1770856881 == hashCode(req.query.passParam)){
        res.sendFile(path.join(__dirname, "src","index.html"));
        console.log("Sesión iniciada correctamente");
    }else{
        console.log("Datos no correctos");
    }

})

/**
 * Indicar donde se encuentran los archivos estáticos
 */
app.use(express.static(path.join(__dirname,"public")));
app.listen(PORT, () => console.log("Running"));