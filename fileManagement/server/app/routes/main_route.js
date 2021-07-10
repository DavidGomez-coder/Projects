const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');

const FILES_PATH = path.join(__dirname, "..","..","..","files");

/**
* Redirect to login 
*/
module.exports = app => {
   
    app.get('/', (req, res) => {
        res.sendFile(path.join(__dirname, "../views/login.html"));
    });

    
}