const path = require('path');
const FILES_PATH = global.FILES_PATH;
const dirTree = require('directory-tree');
const cache = require('memory-cache');
module.exports = app => {

    //go to up directory
    app.get("/upDirectory", async (req, res) => {
        const currentPath = req.query.filePath;
        var previousPath = "";
        if (currentPath == FILES_PATH){
            previousPath = currentPath;
        }else{
            previousPath    = path.join(currentPath, "..");

        }
        var filest;
        if (cache.get(previousPath) == null){
             filest =  await dirTree(previousPath);
             cache.put(filest.path, filest);
        }else{
            filest = cache.get(previousPath);
        }
        
        res.render("index", {filest});
    });
}