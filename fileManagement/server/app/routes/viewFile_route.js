
const videoExtensions = [".mov",".avi",".wmv",".flv",".3gp",".mp4",".mpg"];
const pdfExtension    = [".pdf"];
const textExtensions  = [".txt", ".odt", ".doc", ".docx"];
const pictsExtensions = [".jpeg", ".jpg", ".png", ".gif", ".tiff",".svg"];

const dirTree = require('directory-tree');
const cache = require('memory-cache');
module.exports = app => {

    app.get("/viewMedia", (req, res) => {
        //params to send if file format is supported
        const fileName = req.query.fileName;
        var path = req.query.filePath;
        const fileExtension = req.query.fileExtension;
        const fileSize      = req.query.fileSize;
    
        //file path to the file to view
        const filePath = path.replace(global.FILES_PATH,'');
        const filest =  dirTree(path);
        
        //renders
        if (videoExtensions.includes(fileExtension)){
            console.log("Viewing " + filePath);
            res.render("viewMedia", {type:"video", filest, fileName, filePath, fileExtension, fileSize});
        }else if (pdfExtension.includes(fileExtension)){
            console.log("Viewing " + filePath);
            res.render("viewMedia", {type:"pdf", filest, fileName, filePath, fileExtension, fileSize});
        }else if (pictsExtensions.includes(fileExtension)){
            console.log("Viewing " + filePath);
            res.render("viewMedia", {type:"picture", filest, fileName, filePath, fileExtension, fileSize});
        } else{
            res.render("index", {filest});
        }
    });
}

