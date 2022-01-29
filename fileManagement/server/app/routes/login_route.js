const path = require('path');
const fs = require('fs');
const dirTree = require('directory-tree');
const cache = require('memory-cache');
const FILES_PATH = global.FILES_PATH;


module.exports = app => {
     // Redirect to main page after introduce correct username and password. Only accepts a 
     // a pre-defined user and password (root and vc$$root)
     app.get("/login", async (req, res) => {   
        hashCode = function(s){
            return s.split("").reduce(function(a,b){a=((a<<5)-a)+b.charCodeAt(0);return a&a},0);              
        }
        const filest = dirTree(FILES_PATH);
        res.render("index", {filest});
        /*
        if (-1770856881 == hashCode(req.query.passParam)){
            var count = 0;
            const filest = dirTree(FILES_PATH);
            //console.log(tree);
            //register last treefile
            cache.put(filest.path, filest);
            await res.render("index",{filest});
         }else{
            await res.render("login",{});
            console.log("Fail login: " + req.query.userParam + ", " + req.query.passParam);
        }
        */
    });
}


