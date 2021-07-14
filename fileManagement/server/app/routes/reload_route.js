const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = global.FILES_PATH;

module.exports = app => {

    //it is used to reload the files content
    app.get("/reload", async (req, res) =>{
        var path = req.query.filePath;
        const filest = await dirTree(path);
        console.log("Reload page");
        await res.render("index",{filest});
    });
}