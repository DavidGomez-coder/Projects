const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = global.FILES_PATH;


module.exports = app => {
    app.get("/getDirectory", (req, res) => {

        const tree = dirTree(FILES_PATH);
        var path = req.query.filePath;
        const files = dirTree(path);
        //console.log(files.children);
        res.render("index",{files});
    });
}