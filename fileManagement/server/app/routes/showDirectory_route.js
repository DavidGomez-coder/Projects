const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = path.join(__dirname, "..","..","..","files");


module.exports = app => {
    app.get("/getDirectory", (req, res) => {

        const tree = dirTree(FILES_PATH);
        var path = req.query.filePath;
        const files = dirTree(path);
        //console.log(files.children);
        res.render("index",{tree, files});
    });
}