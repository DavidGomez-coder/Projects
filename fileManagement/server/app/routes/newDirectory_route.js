const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');
const dirTree = require('directory-tree');
const { nextTick } = require('process');

const FILES_PATH = path.join(__dirname, "..","..","..","files");

/**
* It's used to create a new directory on server
*/
module.exports = app => {

     app.get('/createNewDir', async (req, res)  =>  {
        let date = new Date(Date.now());
        let newDirectory = date.getDay()+"-"+date.getMonth()+"-"+date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds();
        var filePath = req.query.newfilePath;
        let name = filePath + "/" + newDirectory;
        if (fs.existsSync(name)){
            console.log("Ya existe un directorio con este nombre");
        }else{
            fs.mkdir(name, function(err){
                if(err){
                    throw('Error: ' + err);
                }
                console.log("\nDirectorio creado: " + name + "\n");
            })
        }

        const tree = dirTree(FILES_PATH);
        const files = dirTree(filePath);
        res.render("index", {tree, files});
    });
}