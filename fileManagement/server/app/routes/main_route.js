const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');
const cache = require('memory-cache');
const dirTree = require('directory-tree');

/**
* Redirect to login 
*/
module.exports = app => {
   
    //method to direct to login form (only accept root params)
    app.get('/', async (req, res) => {
       res.render('login',{});
    });

    
}