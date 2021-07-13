const app = require('./config/server.js');
const path = require('path');

global.FILES_PATH = path.join(__dirname, "..",".","files");

require('./app/routes/main_route.js')(app);
require('./app/routes/login_route.js')(app);
require('./app/routes/upload_route.js')(app);
require('./app/routes/newDirectory_route.js')(app);
require('./app/routes/showDirectory_route.js')(app);
require('./app/routes/upDirectory_route.js')(app);
require('./app/routes/reload_route.js')(app);
require('./app/routes/delDirectory_route.js')(app);
require('./app/routes/download_route.js')(app);


const express = require('express');
const router = express.Router();

//statics elements
app.use('/media', express.static(__dirname + "/app/views/public/media"));
app.use('/images', express.static(__dirname + "/app/views/public/images"));
app.use('/styles',express.static(__dirname + '/app/views/public/styles'));
app.use('/scripts',express.static(__dirname + '/app/views/public/scripts'));

//starting server
app.listen(process.env.PORT || 3000, () => console.log("Running"));
