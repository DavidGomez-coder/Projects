const app = require('./config/server.js');

require('./app/routes/main_route.js')(app);
require('./app/routes/login_route.js')(app);
require('./app/routes/upload_route.js')(app);
require('./app/routes/newDirectory_route.js')(app);

const express = require('express');
const path    = require('path');


//statics elements
app.use('/media', express.static(__dirname + "/public/media"));
app.use('/images', express.static(__dirname + "/public/images"));
app.use('/styles',express.static(__dirname + '/public/styles'));
app.use('/scripts',express.static(__dirname + '/public/scripts'));


//starting server
app.listen(process.env.PORT || 3000, () => console.log("Running"));
