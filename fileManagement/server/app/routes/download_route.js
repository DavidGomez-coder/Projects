
const fs = require('fs');

module.exports = app => {
    app.get("/download", (req, res) => {
        var filePath = req.query.filePath;
        console.log("Downloading " + filePath + " ...");
        res.download(filePath);
    });
}