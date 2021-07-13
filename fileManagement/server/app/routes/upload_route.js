const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');

const FILES_PATH = global.FILES_PATH;

/**
* It's used to post a new file at the selected 
*/
module.exports = app => {
    app.use(fileUpload());

     app.post('/upload', (req, res) => {
        let file = req.files.file;
        let name = FILES_PATH + req.query.dirName;
        file.mv(FILES_PATH+`/${file.name}`, err => {
            if (err) return res.status(500).send({message : err})
            return res.status(200).send({message : 'File Upload'})
        })
    });
}