const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');
const dirTree = require('directory-tree');

const FILES_PATH = global.FILES_PATH;

/**
* It's used to post a new file at the selected 
*/
module.exports = app => {
    app.use(fileUpload());

     app.post('/upload', (req, res) => {
        let f = req.files.file;
        let selectedPath = req.body.filePath ;
   
            console.log("NEW FILE: "  + f + ", to " + selectedPath);
    
            f.mv(FILES_PATH+`/${f.name}`, err => {
                if (err) console.log("ERROR to upload " + f.name);
            });
        
        const filest =  dirTree(FILES_PATH);
        res.render("index", {filest});
       
    });
}