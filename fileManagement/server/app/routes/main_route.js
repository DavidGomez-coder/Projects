const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');


/**
* Redirect to login 
*/
module.exports = app => {
   
    app.get('/', async (req, res) => {
       await res.render('login',{});
    });

    
}