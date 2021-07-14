const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');
const dirTree = require('directory-tree');
const swal = require('sweetalert2');

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
            f.mv(selectedPath+`/${f.name}`, err => {
                if (err) console.log("ERROR to upload " + f.name);
            });

        console.log(" END UPLOADING ........");
        const filest =  dirTree(selectedPath);
        res.render("index", {filest});
       
    });
}