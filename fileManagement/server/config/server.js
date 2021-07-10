const express = require('express');
const path    = require('path');
const bodyParser = require('body-parser');
const { urlencoded } = require('body-parser');

const app = express();


//settings
app.set('port', process.env.PORT || 3000);
app.set('view engine', 'ejs');


//middleware


module.exports = app;