const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = global.FILES_PATH;


module.exports = app => {
    app.get("/getDirectory", async (req, res) => {

        var path = req.query.filePath;
        const files = await dirTree(path);
        //console.log(files.children);
        await res.render("index",{files});
    });
}