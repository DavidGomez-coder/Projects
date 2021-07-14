const path = require('path');
const fs = require('fs');
const fileUpload = require('express-fileupload');


/**
* Redirect to login 
*/
module.exports = app => {
   
    //method to direct to login form (only accept root params)
    app.get('/', async (req, res) => {
       await res.render('login',{});
    });

    
}