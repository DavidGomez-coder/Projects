const path = require('path');
const FILES_PATH = global.FILES_PATH;
const dirTree = require('directory-tree');

module.exports = app => {

    //go to up directory
    app.get("/upDirectory", async (req, res) => {
        const currentPath = await req.query.filePath;
        var previousPath = "";
        if (currentPath == FILES_PATH){
            previousPath = currentPath;
        }else{
            previousPath    = path.join(currentPath, "..");
        }
        const filest = await dirTree(previousPath);
        await res.render("index", {filest});
    });
}