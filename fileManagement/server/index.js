const app = require('./config/server.js');
const path = require('path');
var ip = require("ip");


//global path to files directory
//global.FILES_PATH = path.join(__dirname, "..",".","files");
global.FILES_PATH = "E:\\";

//requirements routes
require('./app/routes/main_route.js')(app);
require('./app/routes/login_route.js')(app);
require('./app/routes/upload_route.js')(app);
require('./app/routes/newDirectory_route.js')(app);
require('./app/routes/showDirectory_route.js')(app);
require('./app/routes/upDirectory_route.js')(app);
require('./app/routes/reload_route.js')(app);
require('./app/routes/delDirectory_route.js')(app);
require('./app/routes/download_route.js')(app);
require('./app/routes/viewFile_route.js')(app);
require('./app/routes/chromecast_route.js')(app);

const express = require('express');
const router = express.Router();

//statics elements
app.use('/media', express.static(global.FILES_PATH));
app.use('/images', express.static(__dirname + "/app/views/public/images"));
app.use('/styles',express.static(__dirname + '/app/views/public/styles'));
app.use('/scripts',express.static(__dirname + '/app/views/public/scripts'));

//starting server
console.dir ( ip.address() );
app.listen(process.env.PORT || 3000, () => console.log("Running"));
