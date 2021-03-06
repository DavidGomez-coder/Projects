const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');
const dirTree = require('directory-tree');
const cache = require('memory-cache');


const FILES_PATH = global.FILES_PATH;


//It's used to create a new directory on server
module.exports = app => {

     app.get('/createNewDir', async (req, res)  =>  {
        //if the user didn't write a name to the new directory, it will be de current date
        let date = new Date(Date.now());
        let newDirectory = req.query.newName;
        if (newDirectory == null || newDirectory==""){
            newDirectory = date.getDay()+"-"+date.getMonth()+"-"+date.getHours()+"-"+date.getMinutes()+"-"+date.getSeconds();
        }
        var filePath = req.query.newfilePath;
        let name = filePath + "/" + newDirectory;
        //direcctory exists
        if (fs.existsSync(name)){
            console.log("Ya existe un directorio con este nombre");
        }else{
            //create an empty directory
            fs.mkdirSync(name);
        }
        const filest = dirTree(path.join(req.query.newfilePath));
        //save in cache
        cache.put(filest.path, filest);
        res.render("index", {filest});

          
    });
}   
