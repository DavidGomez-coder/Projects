const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = global.FILES_PATH;


module.exports = app => {
    app.get("/getDirectory", async (req, res) => {
        //path of the directory we want to go
        var path = req.query.filePath;
        const filest = await dirTree(path);
        //console.log(files.children);
        await res.render("index",{filest});
    });
}