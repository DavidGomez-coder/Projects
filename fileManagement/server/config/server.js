const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');
const { urlencoded } = require('body-parser');
const body_parser = require('body-parser');
const compression = require('compression');

const app = express();


//settings port
app.set('port', process.env.PORT || 3000);

//setting views
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, "..", "app", "views", "src"));

//middleware
app.use(body_parser.urlencoded({ extended: true }));
app.use(express.json());       // to support JSON-encoded bodies
app.use(express.urlencoded()); // to support URL-encoded bodies
app.use(compression());




module.exports = app;