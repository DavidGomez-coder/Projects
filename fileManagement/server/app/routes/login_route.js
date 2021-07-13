const path = require('path');
const fs = require('fs');
const dirTree = require('directory-tree');

const FILES_PATH = global.FILES_PATH;


module.exports = app => {
    /**
     * Redirect to main page after introduce username and password
     */
     app.get("/login", (req, res) => {   
        hashCode = function(s){
            return s.split("").reduce(function(a,b){a=((a<<5)-a)+b.charCodeAt(0);return a&a},0);              
        }
    
        if (-1770856881 == hashCode(req.query.passParam)){
            var count = 0;
            const tree = dirTree(FILES_PATH);
            const files = tree;
            //console.log(tree);
            res.render("index",{files});
         }else{
            res.render("login",{});
            console.log("Fail login: " + req.query.userParam + ", " + req.query.passParam);
        }
    });
}


