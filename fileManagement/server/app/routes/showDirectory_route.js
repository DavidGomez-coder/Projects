const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = global.FILES_PATH;
const cache = require('memory-cache');

module.exports = app => {
    app.get("/getDirectory", async (req, res) => {
        //path of the directory we want to go
        var p = req.query.filePath;
        var filest;

        if (cache.get(p) == null){
            filest = await dirTree(path.join(p,"/"));
            cache.put(p, filest);
        }else{
            filest = cache.get(p);
        }

        //console.log(files.children);
        
        res.render("index",{filest});
    });
}