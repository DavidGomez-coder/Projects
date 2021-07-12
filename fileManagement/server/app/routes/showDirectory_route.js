const path = require('path');

module.exports = app => {
    app.get("/getDirectory", (req, res) => {
        var path = req.query.filePath;
        console.log(path);
        res.status(200).send({message : 'OK'})
    });
}