const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');

const FILES_PATH = path.join(__dirname, "..","..","..","files");

/**
* It's used to create a new directory on server
*/
module.exports = app => {

     app.get('/createNewDir', (req, res) => {
        let name = FILES_PATH + "/" + req.query.dirName;
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
}