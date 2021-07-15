const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = global.FILES_PATH;
const cache = require('memory-cache');
module.exports = app => {

    //it is used to reload the files content
    app.get("/reload", async (req, res) =>{
        var path = req.query.filePath;
        var filest;
        if (cache.get(path) == null){
            filest = dirTree(path);
        }else{
            filest = cache.get(path);
        }
        console.log("Reload page");
        res.render("index",{filest});
    });
}