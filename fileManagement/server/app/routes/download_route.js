
const fs = require('fs');

//method to download a file (not a directory)
module.exports = app => {
    app.get("/download", (req, res) => {
        //file path from form
        var filePath = req.query.filePath;
        console.log("Downloading " + filePath + " ...");

        //download
        res.download(filePath);
    });
}