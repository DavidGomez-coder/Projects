const rimraf = require('rimraf');
const fs = require('fs');
const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = global.FILES_PATH;

module.exports = app => {
    app.get("/deleteDirectory", async (req, res) => {
        var directory = req.query.filePath;
        var newDir = path.join(directory, "..");
        rimraf(directory, function(){
            console.log(directory + " has been removed");
        });
         
        const filest =  dirTree(newDir);
        res.render("index", {filest});
    });

    app.get("/deleteFile", async (req, res ) => {
        var file = req.query.filePath;
        var newDir = path.join(file, "..");
        
        try {
            fs.unlinkSync(file);
            console.log(file + " has been removed");
            const filest = dirTree(newDir);
            res.render("index", {filest});
        }catch(err){
            console.error("Error on file deleting", err);
        }

        
    });
}