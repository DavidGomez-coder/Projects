const path = require('path');
const FILES_PATH = global.FILES_PATH;
const dirTree = require('directory-tree');

module.exports = app => {
    app.get("/upDirectory", (req, res) => {
        const currentPath = req.query.filePath;
        var previousPath = "";
        if (currentPath == FILES_PATH){
            previousPath = currentPath;
        }else{
            previousPath    = path.join(currentPath, "..");
        }
        const tree = dirTree(previousPath);
        const files = tree;
        res.render("index", {files});
    });
}