const rimraf = require('rimraf');
const fs = require('fs');
const path = require('path');
const dirTree = require('directory-tree');
const FILES_PATH = global.FILES_PATH;

module.exports = app => {
    //method to delete a directory
    app.get("/deleteDirectory", async (req, res) => {
        //get directory from form
        var directory = req.query.filePath;
        var newDir = path.join(directory, "..");

        //delete function
        rimraf(directory, function(){
            console.log(directory + " has been removed");
        });
         
        //render ejs
        const filest =  dirTree(newDir);
        res.render("index", {filest});
    });


    //method to delete a file
    app.get("/deleteFile", async (req, res ) => {
        //get file path from form
        var file = req.query.filePath;
        var newDir = path.join(file, "..");
        
        try {
            //delete file function
            fs.unlinkSync(file);
            console.log(file + " has been removed");
            const filest = dirTree(newDir);
            res.render("index", {filest});
        }catch(err){
            //error
            console.error("Error on file deleting", err);
        }

        
    });
}