const express = require('express');
const path    = require('path');
const bodyParser = require('body-parser');
const { urlencoded } = require('body-parser');

const app = express();


//settings port
app.set('port', process.env.PORT || 3000);

//setting views
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, "..", "app", "views", "src"));

//middleware


module.exports = app;