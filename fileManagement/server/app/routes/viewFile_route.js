
const videoExtensions = [".mov",".avi",".wmv",".flv",".3gp",".mp4",".mpg"];
const pdfExtension    = ".pdf";
const textExtensions  = [".txt", ".odt", ".doc", ".docx"];
const pictsExtensions = [".jpeg", ".jpg", ".png", ".gif", ".tiff"];

const dirTree = require('directory-tree');

module.exports = app => {

    app.get("/viewMedia", (req, res) => {
        //params to send if file format is supported
        const fileName = req.query.fileName;
        var path = req.query.filePath;
        const fileExtension = req.query.fileExtension;
        const fileSize      = req.query.fileSize;
    
        //file path to the file to view
        const filePath = path.replace(global.FILES_PATH,'');

        //renders
        if (videoExtensions.includes(fileExtension)){
            console.log("Viewing " + filePath);
            res.render("videoRep", {fileName, filePath, fileExtension, fileSize});
        }else{
            const filest =  dirTree(path);
            res.render("index", {filest});
        }
    });
}

